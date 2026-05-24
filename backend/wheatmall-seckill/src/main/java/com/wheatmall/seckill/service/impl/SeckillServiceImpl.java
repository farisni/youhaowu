package com.wheatmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wheatmall.common.constant.seckill.SeckillConstant;
import com.wheatmall.common.to.seckill.SeckillOrderTO;
import com.wheatmall.common.utils.R;
import com.wheatmall.common.vo.seckill.SeckillSessionWithSkusTO;
import com.wheatmall.common.vo.seckill.SeckillSkuRedisTO;
import com.wheatmall.common.vo.seckill.SeckillSkuVO;
import com.wheatmall.common.vo.seckill.SkuInfoTO;
import com.wheatmall.seckill.interceptor.LoginUserInterceptor;
import com.wheatmall.seckill.remote.CouponRemoteService;
import com.wheatmall.seckill.remote.ProductRemoteService;
import com.wheatmall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.wheatmall.common.constant.seckill.SeckillConstant.SECKILL_CHARE_KEY;

/**
 * 秒杀服务实现
 * 核心流程：定时上架 → Redis 缓存 → 秒杀下单 → Kafka 异步创建订单
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponRemoteService couponRemoteService;
    @Autowired
    private ProductRemoteService productRemoteService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private KafkaTemplate<String, SeckillOrderTO> seckillKafkaTemplate;
    @Autowired
    private RedissonClient redissonClient;

    //  ==================== 上架 ====================

    /**
     * 上架最近三天需要秒杀的商品
     * 1. 远程调用 Coupon 服务获取最近三天秒杀场次 + 商品
     * 2. 上架场次信息到 Redis List
     * 3. 上架商品详情到 Redis Hash
     */
    @Override
    public void uploadSeckillSkuLatest3Days() {
        //  1. 查询最近三天需要参加秒杀的场次 + 商品
        R lates3DaySession = couponRemoteService.getLates3DaySession();
        if (lates3DaySession.getCode() == 0) {
            @SuppressWarnings("unchecked")
            List<SeckillSessionWithSkusTO> sessions = (List<SeckillSessionWithSkusTO>) lates3DaySession.getData(
                    new TypeReference<List<SeckillSessionWithSkusTO>>() {});
            //  2. 上架场次信息
            saveSessionInfos(sessions);
            //  3. 上架商品信息
            saveSessionSkuInfo(sessions);
        }
    }

    /**
     * 上架场次信息到 Redis
     * 以场次开始/结束时间戳为 key，存储 sessionId_skuId 列表
     */
    private void saveSessionInfos(List<SeckillSessionWithSkusTO> sessions) {
        if (CollectionUtils.isEmpty(sessions)) return;

        sessions.forEach(session -> {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SeckillConstant.SESSION_CACHE_PREFIX + startTime + "_" + endTime;

            //  幂等性：已上架则跳过
            Boolean hasKey = redisTemplate.hasKey(key);
            if (!hasKey) {
                List<String> skuIds = session.getRelationSkus().stream()
                        .map(item -> item.getPromotionSessionId() + "_" + item.getSkuId().toString())
                        .collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key, skuIds);
            }
        });
    }

    /**
     * 上架商品详情到 Redis Hash
     * 1. 收集所有 SKU ID，批量查 Product 服务
     * 2. 遍历场次，将 SeckillSkuRedisTO 序列化写入 Hash
     */
    private void saveSessionSkuInfo(List<SeckillSessionWithSkusTO> sessions) {
        if (CollectionUtils.isEmpty(sessions)) return;

        //  收集所有 SKU ID
        List<Long> skuIds = new ArrayList<>();
        sessions.forEach(session -> {
            List<Long> ids = session.getRelationSkus().stream()
                    .map(SeckillSkuVO::getSkuId).collect(Collectors.toList());
            skuIds.addAll(ids);
        });

        //  批量查询 Product 服务
        R info = productRemoteService.getSkuInfos(skuIds);
        if (info.getCode() == 0) {
            //  构建 SKU ID → SkuInfoTO 映射
            @SuppressWarnings("unchecked")
            List<SkuInfoTO> skuInfoList = (List<SkuInfoTO>) info.getData(new TypeReference<List<SkuInfoTO>>() {});
            Map<Long, SkuInfoTO> skuMap = skuInfoList
                    .stream().collect(Collectors.toMap(SkuInfoTO::getSkuId, v -> v));

            BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SECKILL_CHARE_KEY);

            //  遍历场次，组装并写入秒杀商品信息
            sessions.forEach(session -> {
                session.getRelationSkus().forEach(seckillSku -> {
                    //  判断是否已上架（幂等性）
                    String redisKey = seckillSku.getPromotionSessionId() + "_" + seckillSku.getSkuId();
                    if (!ops.hasKey(redisKey)) {
                        //  组装 SeckillSkuRedisTO
                        SeckillSkuRedisTO redisTO = new SeckillSkuRedisTO();
                        redisTO.setPromotionId(seckillSku.getPromotionId());
                        redisTO.setPromotionSessionId(seckillSku.getPromotionSessionId());
                        redisTO.setSkuId(seckillSku.getSkuId());
                        redisTO.setSeckillPrice(seckillSku.getSeckillPrice());
                        redisTO.setSeckillCount(seckillSku.getSeckillCount());
                        redisTO.setSeckillLimit(seckillSku.getSeckillLimit());
                        redisTO.setSeckillSort(seckillSku.getSeckillSort());
                        redisTO.setStartTime(session.getStartTime().getTime());
                        redisTO.setEndTime(session.getEndTime().getTime());

                        //  随机码（防刷）
                        String randomCode = UUID.randomUUID().toString().replace("-", "");
                        redisTO.setRandomCode(randomCode);

                        //  填充 SKU 基本信息
                        SkuInfoTO skuInfo = skuMap.get(seckillSku.getSkuId());
                        if (skuInfo != null) {
                            redisTO.setSkuInfo(skuInfo);
                        }

                        //  信号量（库存）
                        RSemaphore semaphore = redissonClient.getSemaphore(
                                SeckillConstant.SKU_STOCK_SEMAPHORE + randomCode);
                        semaphore.trySetPermits(seckillSku.getSeckillCount());

                        //  写入 Redis Hash
                        ops.put(redisKey, JSON.toJSONString(redisTO));
                    }
                });
            });
        }
    }

    //  ==================== 查询 ====================

    /**
     * 查询当前时间可以参与秒杀的商品列表
     * 1. 遍历 Redis 中所有秒杀场次 key
     * 2. 筛选当前时间在有效期内的场次
     * 3. 从 Hash 中取出对应商品信息返回
     */
    @Override
    public List<SeckillSkuRedisTO> getCurrentSeckillSkus() {
        //  获取所有秒杀场次 key
        Set<String> keys = redisTemplate.keys(SeckillConstant.SESSION_CACHE_PREFIX + "*");
        if (CollectionUtils.isEmpty(keys)) return null;

        long currentTime = System.currentTimeMillis();
        List<SeckillSkuRedisTO> result = new ArrayList<>();
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SECKILL_CHARE_KEY);

        for (String key : keys) {
            //  解析场次时间：seckill:sessions:startTime_endTime
            String[] times = key.replace(SeckillConstant.SESSION_CACHE_PREFIX, "").split("_");
            long startTime = Long.parseLong(times[0]);
            long endTime = Long.parseLong(times[1]);

            //  筛选当前时间在有效期内的场次
            if (currentTime >= startTime && currentTime <= endTime) {
                List<String> skuIds = redisTemplate.opsForList().range(key, -100, 100);
                if (!CollectionUtils.isEmpty(skuIds)) {
                    List<Object> values = ops.multiGet(new ArrayList<>(skuIds));
                    if (!CollectionUtils.isEmpty(values)) {
                        values.forEach(v -> {
                            SeckillSkuRedisTO to = JSON.parseObject((String) v, SeckillSkuRedisTO.class);
                            result.add(to);
                        });
                    }
                }
            }
        }
        return result;
    }

    @Override
    public SeckillSkuRedisTO getSkuSeckilInfo(Long skuId) {
        //  遍历所有场次，查找匹配的 SKU
        Set<String> keys = redisTemplate.keys(SeckillConstant.SESSION_CACHE_PREFIX + "*");
        if (CollectionUtils.isEmpty(keys)) return null;

        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SECKILL_CHARE_KEY);
        long currentTime = System.currentTimeMillis();

        for (String key : keys) {
            String[] times = key.replace(SeckillConstant.SESSION_CACHE_PREFIX, "").split("_");
            long startTime = Long.parseLong(times[0]);
            long endTime = Long.parseLong(times[1]);

            if (currentTime >= startTime && currentTime <= endTime) {
                List<String> skuIdList = redisTemplate.opsForList().range(key, -100, 100);
                if (!CollectionUtils.isEmpty(skuIdList)) {
                    for (String s : skuIdList) {
                        if (s.contains(skuId.toString())) {
                            String json = (String) ops.get(s);
                            if (json != null) {
                                return JSON.parseObject(json, SeckillSkuRedisTO.class);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    //  ==================== 秒杀下单 ====================

    /**
     * 秒杀商品
     * 1. 校验登录状态（拦截器已完成）
     * 2. 校验秒杀时间是否在有效期内
     * 3. 校验随机码是否匹配
     * 4. 校验信号量（库存）+ 限购数量
     * 5. 校验是否重复秒杀（SETNX 幂等占位）
     * 6. 扣减信号量（Redisson Semaphore）
     * 7. 发送 Kafka 消息，由订单模块消费生成订单
     */
    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {
        long start = System.currentTimeMillis();

        //  1. 获取当前用户 ID
        Long userId = LoginUserInterceptor.loginUserId.get();

        //  2. 校验秒杀场次 + 商品是否存在
        BoundHashOperations<String, Object, Object> skuOps = redisTemplate.boundHashOps(SECKILL_CHARE_KEY);
        String jsonString = (String) skuOps.get(killId);
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        SeckillSkuRedisTO skuInfo = JSON.parseObject(jsonString, SeckillSkuRedisTO.class);

        //  3. 校验秒杀时间
        long currentTime = System.currentTimeMillis();
        if (currentTime < skuInfo.getStartTime() || currentTime > skuInfo.getEndTime()) {
            log.info("秒杀失败，不在有效时间范围内，耗时..." + (System.currentTimeMillis() - start));
            return null;
        }

        //  4. 校验随机码
        if (!skuInfo.getRandomCode().equals(key)) {
            log.info("秒杀失败，随机码不匹配，耗时..." + (System.currentTimeMillis() - start));
            return null;
        }

        //  5. 校验信号量 + 限购数量
        String stockKey = SeckillConstant.SKU_STOCK_SEMAPHORE + key;
        String countStr = redisTemplate.opsForValue().get(stockKey);
        int count = Integer.parseInt(countStr);
        if (num <= 0 || num > skuInfo.getSeckillLimit() || count < num) {
            log.info("秒杀失败，库存不足或超限购，耗时..." + (System.currentTimeMillis() - start));
            return null;
        }

        //  6. 校验是否重复秒杀（SETNX 幂等占位）
        String userKey = SeckillConstant.SECKILL_USER_PREFIX + userId + "_" + killId;
        long ttl = skuInfo.getEndTime() - currentTime;
        Boolean isRepeat = redisTemplate.opsForValue().setIfAbsent(userKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
        if (!isRepeat) {
            log.info("秒杀失败，重复下单，耗时..." + (System.currentTimeMillis() - start));
            return null;
        }

        //  7. 扣减信号量（Redisson Semaphore，防超卖）
        RSemaphore semaphore = redissonClient.getSemaphore(stockKey);
        boolean isAcquire = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
        if (!isAcquire) {
            log.info("秒杀失败，信号量扣减失败，耗时..." + (System.currentTimeMillis() - start));
            return null;
        }

        //  8. 组装订单消息，发送 Kafka
        String orderSn = UUID.randomUUID().toString().replace("-", "");
        SeckillOrderTO order = new SeckillOrderTO();
        order.setOrderSn(orderSn);
        order.setMemberId(userId);
        order.setNum(num);
        order.setPromotionSessionId(skuInfo.getPromotionSessionId());
        order.setSkuId(skuInfo.getSkuId());
        order.setSeckillPrice(skuInfo.getSeckillPrice());

        //  TODO: 可靠消息（生产者确认 + 消费者手动提交）
        seckillKafkaTemplate.send("seckill-order-topic", orderSn, order);

        long end = System.currentTimeMillis();
        log.info("秒杀成功，订单号={}，耗时={}ms", orderSn, end - start);
        return orderSn;
    }
}

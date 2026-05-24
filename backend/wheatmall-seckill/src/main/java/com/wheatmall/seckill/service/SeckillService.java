package com.wheatmall.seckill.service;

import com.wheatmall.common.vo.seckill.SeckillSkuRedisTO;

import java.util.List;

/**
 * 秒杀服务接口
 */
public interface SeckillService {

    /**
     * 上架最近三天需要秒杀的商品
     */
    void uploadSeckillSkuLatest3Days();

    /**
     * 查询当前时间可以参与秒杀的商品列表
     */
    List<SeckillSkuRedisTO> getCurrentSeckillSkus();

    /**
     * 根据 skuId 查询商品当前时间秒杀信息
     */
    SeckillSkuRedisTO getSkuSeckilInfo(Long skuId);

    /**
     * 秒杀商品
     * 1. 校验登录状态（拦截器）
     * 2. 校验秒杀时间
     * 3. 校验随机码、场次、商品对应关系
     * 4. 校验信号量扣减，校验购物数量是否限购
     * 5. 校验是否重复秒杀（幂等性，SETNX 占位）
     * 6. 扣减信号量（Redisson Semaphore）
     * 7. 发送 Kafka 消息，订单模块消费生成订单
     * @param killId  sessionId_skuid
     * @param key     随机码
     * @param num     商品件数
     * @return 订单号，失败返回 null
     */
    String kill(String killId, String key, Integer num) throws InterruptedException;
}

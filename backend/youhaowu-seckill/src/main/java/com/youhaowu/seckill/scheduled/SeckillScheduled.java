package com.youhaowu.seckill.scheduled;

import com.youhaowu.common.constant.seckill.SeckillConstant;
import com.youhaowu.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品定时上架
 * 每天晚上 3 点上架最近三天需要秒杀的商品（用分布式锁保证幂等）
 */
@Slf4j
@Component
public class SeckillScheduled {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 秒杀商品定时上架
     * 每天晚上 3 点执行：0 0 3 * * ?
     * 开发阶段每 10 秒执行一次（cron: 0/10 * * * * ?）
     */
    @Scheduled(cron = "*/10 * * * * ? ")
    public void uploadSeckillSkuLatest3Days() {
        log.info("上架秒杀的商品...");

        //  分布式锁（幂等性）
        RLock lock = redissonClient.getLock(SeckillConstant.UPLOAD_LOCK);
        try {
            lock.lock(10, TimeUnit.SECONDS);
            seckillService.uploadSeckillSkuLatest3Days();
        } catch (Exception e) {
            log.error("秒杀商品上架失败", e);
        } finally {
            lock.unlock();
        }
    }
}

package com.youhaowu.common.constant.seckill;

public class SeckillConstant {
    // 秒杀商品上架功能的锁
    public static final String UPLOAD_LOCK = "youhaowu-seckill:upload:lock";
    // 秒杀场次key
    public static final String SESSION_CACHE_PREFIX = "youhaowu-seckill:sessions:";
    // 秒杀商品key
    public static final String SECKILL_CHARE_KEY = "youhaowu-seckill:skus";
    // 商品随机码
    public static final String SKU_STOCK_SEMAPHORE = "youhaowu-seckill:stock:";
    // 用户占位key
    public static final String SECKILL_USER_PREFIX = "youhaowu-seckill:user:";
}

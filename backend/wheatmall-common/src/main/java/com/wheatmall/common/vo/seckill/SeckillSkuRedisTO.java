package com.wheatmall.common.vo.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Redis 中存放的秒杀商品信息
 */
@Data
public class SeckillSkuRedisTO {

    private Long promotionId;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private Integer seckillCount;
    private Integer seckillLimit;
    private Integer seckillSort;

    /** SKU 基本信息 */
    private SkuInfoTO skuInfo;

    /** 秒杀开始时间（毫秒时间戳） */
    private Long startTime;

    /** 秒杀结束时间（毫秒时间戳） */
    private Long endTime;

    /** 秒杀随机码（防刷） */
    private String randomCode;
}

package com.wheatmall.common.vo.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀场次关联的 SKU 信息（来自 Coupon 服务）
 */
@Data
public class SeckillSkuVO {

    private Long promotionId;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private Integer seckillCount;
    private Integer seckillLimit;
    private Integer seckillSort;
    private Long startTime;
    private Long endTime;
    private String randomCode;
}

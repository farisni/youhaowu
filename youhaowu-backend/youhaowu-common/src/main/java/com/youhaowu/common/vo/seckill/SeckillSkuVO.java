package com.youhaowu.common.vo.seckill;

import java.math.BigDecimal;

public record SeckillSkuVO(
    Long promotionId,
    Long promotionSessionId,
    Long skuId,
    BigDecimal seckillPrice,
    Integer seckillCount,
    Integer seckillLimit,
    Integer seckillSort,
    Long startTime,
    Long endTime,
    String randomCode
) {}

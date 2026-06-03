package com.youhaowu.common.vo.seckill;

import java.math.BigDecimal;

public record SkuInfoTO(
    Long skuId,
    Long spuId,
    String skuName,
    String skuDesc,
    Long catalogId,
    Long brandId,
    String skuDefaultImg,
    String skuTitle,
    String skuSubtitle,
    BigDecimal price,
    Long saleCount
) {}

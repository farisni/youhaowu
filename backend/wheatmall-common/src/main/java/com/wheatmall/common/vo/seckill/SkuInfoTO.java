package com.wheatmall.common.vo.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀模块 SKU 基本信息（从 Product 服务远程查询）
 */
@Data
public class SkuInfoTO {

    private Long skuId;
    private Long spuId;
    private String skuName;
    private String skuDesc;
    private Long catalogId;
    private Long brandId;
    private String skuDefaultImg;
    private String skuTitle;
    private String skuSubtitle;
    private BigDecimal price;
    private Long saleCount;
}

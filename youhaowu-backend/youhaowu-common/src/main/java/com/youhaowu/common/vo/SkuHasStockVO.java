package com.youhaowu.common.vo;

import lombok.Data;

/**
 * SKU 库存查询结果
 */
@Data
public class SkuHasStockVO {
    private Long skuId;
    private Boolean hasStock;
}

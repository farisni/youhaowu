package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SkuSaleAttrValue VO
 */
@Data
public class SkuSaleAttrValueVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 SkuSaleAttrValueEntity 一致，通过 BeanUtil.copyProperties 转换
}

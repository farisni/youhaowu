package com.youhaowu.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SkuSaleAttrValue VO
 */
@Data
public class SkuSaleAttrValueVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long skuId;
    private Long attrId;
    private String attrName;
    private String attrValue;
    private Integer attrSort;
}

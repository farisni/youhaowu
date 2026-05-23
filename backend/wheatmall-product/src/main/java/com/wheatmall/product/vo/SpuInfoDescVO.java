package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SpuInfoDesc VO
 */
@Data
public class SpuInfoDescVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 商品id */
    private Long spuId;
    /** 商品介绍 */
    private String decript;
}

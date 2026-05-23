package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SkuImages VO
 */
@Data
public class SkuImagesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long skuId;
    private String imgUrl;
    private Integer imgSort;
    private Integer defaultImg;
}

package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SpuImages VO
 */
@Data
public class SpuImagesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long spuId;
    private String imgName;
    private String imgUrl;
    private Integer imgSort;
    private Integer defaultImg;
}

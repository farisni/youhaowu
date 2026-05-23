package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SkuImages VO
 */
@Data
public class SkuImagesVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 SkuImagesEntity 一致，通过 BeanUtils.copyProperties 转换
}

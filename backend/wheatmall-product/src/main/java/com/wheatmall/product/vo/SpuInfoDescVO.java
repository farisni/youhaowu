package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SpuInfoDesc VO
 */
@Data
public class SpuInfoDescVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 SpuInfoDescEntity 一致，通过 BeanUtil.copyProperties 转换
}

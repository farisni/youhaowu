package com.youhaowu.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SpuInfo VO
 */
@Data
public class SpuInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 SpuInfoEntity 一致，通过 BeanUtil.copyProperties 转换
}

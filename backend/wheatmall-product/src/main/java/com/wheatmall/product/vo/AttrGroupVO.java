package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * AttrGroup VO
 */
@Data
public class AttrGroupVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 AttrGroupEntity 一致，通过 BeanUtils.copyProperties 转换
}

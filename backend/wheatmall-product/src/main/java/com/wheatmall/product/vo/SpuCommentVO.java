package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * SpuComment VO
 */
@Data
public class SpuCommentVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 SpuCommentEntity 一致，通过 BeanUtils.copyProperties 转换
}

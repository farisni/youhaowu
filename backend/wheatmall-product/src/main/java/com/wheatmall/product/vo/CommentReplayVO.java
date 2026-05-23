package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * CommentReplay VO
 */
@Data
public class CommentReplayVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 CommentReplayEntity 一致，通过 BeanUtils.copyProperties 转换
}

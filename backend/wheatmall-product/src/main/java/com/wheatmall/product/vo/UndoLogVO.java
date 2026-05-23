package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * UndoLog VO
 */
@Data
public class UndoLogVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 UndoLogEntity 一致，通过 BeanUtil.copyProperties 转换
}

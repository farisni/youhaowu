package com.wheatmall.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * OrderReturnApply VO
 */
@Data
public class OrderReturnApplyVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderReturnApplyEntity 一致，通过 BeanUtil.copyProperties 转换
}

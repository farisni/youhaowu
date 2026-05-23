package com.wheatmall.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * OrderReturnReason VO
 */
@Data
public class OrderReturnReasonVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderReturnReasonEntity 一致，通过 BeanUtil.copyProperties 转换
}

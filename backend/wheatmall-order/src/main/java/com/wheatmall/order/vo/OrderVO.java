package com.wheatmall.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * Order VO
 */
@Data
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderEntity 一致，通过 BeanUtil.copyProperties 转换
}

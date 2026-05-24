package com.youhaowu.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * OrderItem VO
 */
@Data
public class OrderItemVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderItemEntity 一致，通过 BeanUtil.copyProperties 转换
}

package com.youhaowu.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * OrderOperateHistory VO
 */
@Data
public class OrderOperateHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderOperateHistoryEntity 一致，通过 BeanUtil.copyProperties 转换
}

package com.youhaowu.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * MqMessage VO
 */
@Data
public class MqMessageVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 MqMessageEntity 一致，通过 BeanUtil.copyProperties 转换
}

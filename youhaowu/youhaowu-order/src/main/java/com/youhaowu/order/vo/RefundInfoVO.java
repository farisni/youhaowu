package com.youhaowu.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * RefundInfo VO
 */
@Data
public class RefundInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 RefundInfoEntity 一致，通过 BeanUtil.copyProperties 转换
}

package com.wheatmall.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * PaymentInfo VO
 */
@Data
public class PaymentInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 PaymentInfoEntity 一致，通过 BeanUtil.copyProperties 转换
}

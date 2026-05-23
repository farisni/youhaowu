package com.wheatmall.order.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * OrderSetting VO
 */
@Data
public class OrderSettingVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 OrderSettingEntity 一致，通过 BeanUtil.copyProperties 转换
}

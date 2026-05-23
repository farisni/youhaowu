package com.wheatmall.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * PurchaseDetail VO
 */
@Data
public class PurchaseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 PurchaseDetailEntity 一致，通过 BeanUtil.copyProperties 转换
}

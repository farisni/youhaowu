package com.youhaowu.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * Purchase VO
 */
@Data
public class PurchaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 PurchaseEntity 一致，通过 BeanUtil.copyProperties 转换
}

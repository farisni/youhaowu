package com.youhaowu.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * WareInfo VO
 */
@Data
public class WareInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 WareInfoEntity 一致，通过 BeanUtil.copyProperties 转换
}

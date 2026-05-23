package com.wheatmall.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * WareSku VO
 */
@Data
public class WareSkuVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 WareSkuEntity 一致，通过 BeanUtil.copyProperties 转换
}

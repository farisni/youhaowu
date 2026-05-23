package com.wheatmall.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * WareOrderTask VO
 */
@Data
public class WareOrderTaskVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 WareOrderTaskEntity 一致，通过 BeanUtil.copyProperties 转换
}

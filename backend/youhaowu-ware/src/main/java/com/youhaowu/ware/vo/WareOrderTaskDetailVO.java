package com.youhaowu.ware.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * WareOrderTaskDetail VO
 */
@Data
public class WareOrderTaskDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    // 字段与 WareOrderTaskDetailEntity 一致，通过 BeanUtil.copyProperties 转换
}

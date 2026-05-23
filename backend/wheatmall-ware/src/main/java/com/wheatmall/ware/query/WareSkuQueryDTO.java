package com.wheatmall.ware.query;

import com.wheatmall.common.dto.BaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * WareSku 查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WareSkuQueryDTO extends BaseQueryDTO {
    /** 关键字搜索 */
    private String keyword;
}

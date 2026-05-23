package com.wheatmall.ware.query;

import com.wheatmall.common.dto.BaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Purchase 查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseQueryDTO extends BaseQueryDTO {
    /** 关键字搜索 */
    private String keyword;
}

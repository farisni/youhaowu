package com.wheatmall.order.query;

import com.wheatmall.common.dto.BaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Order 查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderQueryDTO extends BaseQueryDTO {
    /** 关键字搜索 */
    private String keyword;
}

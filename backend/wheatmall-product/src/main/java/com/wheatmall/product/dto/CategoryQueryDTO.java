package com.wheatmall.product.dto;

import com.wheatmall.common.dto.BaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分类查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryQueryDTO extends BaseQueryDTO {
    /** 分类名称（模糊） */
    private String name;
}

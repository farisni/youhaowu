package com.wheatmall.product.dto;

import lombok.Data;

/**
 * 分类查询参数
 */
@Data
public class CategoryQueryDTO {
    /** 分类名称（模糊） */
    private String name;
    /** 关键字 */
    private String keyword;
    /** 当前页码 */
    private Long pageNum = 1L;
    /** 每页条数 */
    private Long pageSize = 10L;
}

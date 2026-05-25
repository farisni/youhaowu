package com.youhaowu.product.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 分类排序更新 DTO（拖拽排序用）
 */
@Data
public class CategorySortUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long catId;
    private Integer sort;
    private Long parentCid;
}

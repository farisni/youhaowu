package com.youhaowu.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类更新 DTO
 */
@Data
public class CategoryUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分类ID不能为空")
    private Long catId;
    private String name;
    private String icon;
    private Integer sort;
    private Integer showStatus;
    private String productUnit;
}

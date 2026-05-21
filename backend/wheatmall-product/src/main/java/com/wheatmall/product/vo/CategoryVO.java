package com.wheatmall.product.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类 VO - 返回前端展示
 */
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long catId;
    private String name;
    private Long parentCid;
    private Integer catLevel;
    private Integer showStatus;
    private Integer sort;
    private String icon;
    private String productUnit;
    private Integer productCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryVO> children;
}

package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * spu属性值 VO
 */
@Data
public class ProductAttrValueVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long spuId;
    private Long attrId;
    private String attrName;
    private String attrValue;
    private Integer attrSort;
    private Integer quickShow;
}

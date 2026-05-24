package com.youhaowu.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * AttrAttrgroupRelation VO
 */
@Data
public class AttrAttrgroupRelationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long attrId;
    private Long attrGroupId;
    private Integer attrSort;
}

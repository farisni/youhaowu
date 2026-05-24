package com.youhaowu.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVO {
    private Long attrGroupId;
    private String attrGroupName;
    private Integer sort;
    private String descript;
    private String icon;
    private Long catelogId;
    private List<AttrRespVO> attrs;
}

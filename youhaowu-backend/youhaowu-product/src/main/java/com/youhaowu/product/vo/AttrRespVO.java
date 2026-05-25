package com.youhaowu.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttrRespVO extends AttrVO {

    /**
     *
     */
    private String catelogName;

    private String groupName;

    private Long[] catelogPath;

}

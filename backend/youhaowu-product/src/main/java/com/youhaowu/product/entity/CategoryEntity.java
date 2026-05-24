package com.youhaowu.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long catId;
    private String name;
    private Long parentCid;
    private Integer catLevel;
    @TableLogic(value = "1", delval = "0")
    private Integer showStatus;
    private Integer sort;
    private String icon;
    private String productUnit;
    private Integer productCount;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryEntity> children;
}

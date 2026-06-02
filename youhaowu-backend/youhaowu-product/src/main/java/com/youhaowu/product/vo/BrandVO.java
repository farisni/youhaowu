package com.youhaowu.product.vo;

import lombok.Data;

/**
 * 品牌视图对象
 */
@Data
public class BrandVO {

    private Long brandId;

    private String name;

    private String logo;
    private String fileName;

    private String descript;

    private Integer showStatus;

    private String firstLetter;

    private Integer sort;

}

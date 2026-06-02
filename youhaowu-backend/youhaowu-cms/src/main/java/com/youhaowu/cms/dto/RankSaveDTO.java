package com.youhaowu.cms.dto;

import lombok.Data;

@Data
public class RankSaveDTO {
    private Long id;
    private String title;
    private String imgUrl;
    private String price;
    private String tabType;
    private Integer sort;
    private Integer status;
}

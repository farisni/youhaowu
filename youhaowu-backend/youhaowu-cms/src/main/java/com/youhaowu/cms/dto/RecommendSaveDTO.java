package com.youhaowu.cms.dto;

import lombok.Data;

@Data
public class RecommendSaveDTO {
    private Long id;
    private String title;
    private String imgUrl;
    private String fileName;
    private String linkUrl;
    private Integer sort;
    private Integer status;
}

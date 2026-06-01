package com.youhaowu.cms.dto;

import lombok.Data;

@Data
public class BannerSaveDTO {
    private String title;
    private String imgUrl;
    private String linkUrl;
    private Integer sort;
    private Integer status;
}

package com.youhaowu.cms.dto;

import lombok.Data;

@Data
public class BannerQueryDTO {
    private Integer page = 1;
    private Integer limit = 10;
    private String title;
    private Integer status;
}

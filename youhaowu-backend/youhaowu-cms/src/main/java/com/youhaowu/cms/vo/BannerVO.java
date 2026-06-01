package com.youhaowu.cms.vo;

import lombok.Data;

@Data
public class BannerVO {
    private Long id;
    private String title;
    private String imgUrl;
    private String linkUrl;
    private Integer sort;
}

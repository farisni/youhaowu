package com.youhaowu.cms.vo;

import lombok.Data;

@Data
public class NewsVO {
    private Long id;
    private String tag;
    private String title;
    private String linkUrl;
    private Integer sort;
}

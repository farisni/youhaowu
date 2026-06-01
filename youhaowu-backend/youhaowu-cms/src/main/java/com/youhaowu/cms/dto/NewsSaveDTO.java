package com.youhaowu.cms.dto;

import lombok.Data;

@Data
public class NewsSaveDTO {
    private String tag;
    private String title;
    private String linkUrl;
    private Integer sort;
    private Integer status;
}

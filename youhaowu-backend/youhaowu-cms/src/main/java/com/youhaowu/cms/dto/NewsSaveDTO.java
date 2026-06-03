package com.youhaowu.cms.dto;


public record NewsSaveDTO(
    String tag,
    String title,
    String linkUrl,
    Integer sort,
    Integer status
) {}

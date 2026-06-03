package com.youhaowu.cms.dto;


public record BannerSaveDTO(
    String title,
    String imgUrl,
    String fileName,
    String linkUrl,
    Integer sort,
    Integer status
) {}

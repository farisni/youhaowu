package com.youhaowu.cms.dto;


public record RecommendSaveDTO(
    Long id,
    String title,
    String imgUrl,
    String fileName,
    String linkUrl,
    Integer sort,
    Integer status
) {}

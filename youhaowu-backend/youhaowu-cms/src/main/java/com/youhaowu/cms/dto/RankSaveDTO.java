package com.youhaowu.cms.dto;


public record RankSaveDTO(
    Long id,
    String title,
    String imgUrl,
    String price,
    String tabType,
    Integer sort,
    Integer status
) {}

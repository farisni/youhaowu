package com.youhaowu.cms.dto;

import com.youhaowu.common.dto.BaseQueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BannerQueryDTO extends BaseQueryDTO {
    private String title;
    private Integer status;
}

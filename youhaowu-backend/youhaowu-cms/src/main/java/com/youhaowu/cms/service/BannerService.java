package com.youhaowu.cms.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.BannerQueryDTO;
import com.youhaowu.cms.dto.BannerSaveDTO;
import com.youhaowu.cms.vo.BannerVO;

import java.util.List;

public interface BannerService {
    List<BannerVO> listEnabled();
    BannerVO getById(Long id);
    PageData<BannerVO> page(BannerQueryDTO query);
    Integer save(BannerSaveDTO dto);
    Integer update(Long id, BannerSaveDTO dto);
    Integer deleteBatch(List<Long> ids);
}

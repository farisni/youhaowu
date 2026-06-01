package com.youhaowu.cms.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.BannerQueryDTO;
import com.youhaowu.cms.dto.BannerSaveDTO;
import com.youhaowu.cms.vo.BannerVO;

import java.util.List;

public interface BannerService {
    List<BannerVO> listEnabled();
    PageData<BannerVO> page(BannerQueryDTO query);
    void save(BannerSaveDTO dto);
    void update(Long id, BannerSaveDTO dto);
    void deleteBatch(List<Long> ids);
}

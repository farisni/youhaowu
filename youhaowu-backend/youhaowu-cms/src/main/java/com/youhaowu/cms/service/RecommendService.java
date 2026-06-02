package com.youhaowu.cms.service;

import com.youhaowu.cms.dto.RecommendQueryDTO;
import com.youhaowu.cms.dto.RecommendSaveDTO;
import com.youhaowu.cms.vo.RecommendVO;
import com.youhaowu.common.utils.PageData;

import java.util.List;

public interface RecommendService {
    List<RecommendVO> listEnabled();
    RecommendVO getById(Long id);
    PageData<RecommendVO> page(RecommendQueryDTO query);
    Integer save(RecommendSaveDTO dto);
    Integer deleteBatch(List<Long> ids);
}

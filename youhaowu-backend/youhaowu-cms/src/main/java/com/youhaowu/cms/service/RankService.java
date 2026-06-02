package com.youhaowu.cms.service;

import com.youhaowu.cms.dto.RankQueryDTO;
import com.youhaowu.cms.dto.RankSaveDTO;
import com.youhaowu.cms.vo.RankVO;
import com.youhaowu.common.utils.PageData;

import java.util.List;

public interface RankService {
    List<RankVO> listByTab(String tabType);
    RankVO getById(Long id);
    PageData<RankVO> page(RankQueryDTO query);
    Integer save(RankSaveDTO dto);
    Integer deleteBatch(List<Long> ids);
}

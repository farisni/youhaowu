package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.GrowthChangeHistoryQueryDTO;
import com.youhaowu.member.vo.GrowthChangeHistoryVO;

import java.util.List;

public interface GrowthChangeHistoryService {
    PageData<GrowthChangeHistoryVO> page(GrowthChangeHistoryQueryDTO query);
    GrowthChangeHistoryVO getById(Long id);
    Integer save(GrowthChangeHistoryVO vo);
    Integer saveBatch(List<GrowthChangeHistoryVO> list);
    Integer updateById(Long id, GrowthChangeHistoryVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

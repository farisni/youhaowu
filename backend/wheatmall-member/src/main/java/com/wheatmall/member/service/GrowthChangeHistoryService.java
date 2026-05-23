package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.GrowthChangeHistoryQueryDTO;
import com.wheatmall.member.vo.GrowthChangeHistoryVO;

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

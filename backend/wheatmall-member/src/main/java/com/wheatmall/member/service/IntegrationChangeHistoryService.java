package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.IntegrationChangeHistoryQueryDTO;
import com.wheatmall.member.vo.IntegrationChangeHistoryVO;

import java.util.List;

public interface IntegrationChangeHistoryService {
    PageData<IntegrationChangeHistoryVO> page(IntegrationChangeHistoryQueryDTO query);
    IntegrationChangeHistoryVO getById(Long id);
    Integer save(IntegrationChangeHistoryVO vo);
    Integer saveBatch(List<IntegrationChangeHistoryVO> list);
    Integer updateById(Long id, IntegrationChangeHistoryVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

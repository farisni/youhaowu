package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.IntegrationChangeHistoryQueryDTO;
import com.youhaowu.member.vo.IntegrationChangeHistoryVO;

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

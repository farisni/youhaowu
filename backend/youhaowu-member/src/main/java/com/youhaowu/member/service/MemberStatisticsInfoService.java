package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberStatisticsInfoQueryDTO;
import com.youhaowu.member.vo.MemberStatisticsInfoVO;

import java.util.List;

public interface MemberStatisticsInfoService {
    PageData<MemberStatisticsInfoVO> page(MemberStatisticsInfoQueryDTO query);
    MemberStatisticsInfoVO getById(Long id);
    Integer save(MemberStatisticsInfoVO vo);
    Integer saveBatch(List<MemberStatisticsInfoVO> list);
    Integer updateById(Long id, MemberStatisticsInfoVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

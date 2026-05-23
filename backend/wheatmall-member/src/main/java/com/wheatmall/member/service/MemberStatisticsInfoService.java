package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.MemberStatisticsInfoQueryDTO;
import com.wheatmall.member.vo.MemberStatisticsInfoVO;

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

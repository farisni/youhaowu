package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.MemberLoginLogQueryDTO;
import com.wheatmall.member.vo.MemberLoginLogVO;

import java.util.List;

public interface MemberLoginLogService {
    PageData<MemberLoginLogVO> page(MemberLoginLogQueryDTO query);
    MemberLoginLogVO getById(Long id);
    Integer save(MemberLoginLogVO vo);
    Integer saveBatch(List<MemberLoginLogVO> list);
    Integer updateById(Long id, MemberLoginLogVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

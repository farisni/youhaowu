package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberLoginLogQueryDTO;
import com.youhaowu.member.vo.MemberLoginLogVO;

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

package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberCollectSubjectQueryDTO;
import com.youhaowu.member.vo.MemberCollectSubjectVO;

import java.util.List;

public interface MemberCollectSubjectService {
    PageData<MemberCollectSubjectVO> page(MemberCollectSubjectQueryDTO query);
    MemberCollectSubjectVO getById(Long id);
    Integer save(MemberCollectSubjectVO vo);
    Integer saveBatch(List<MemberCollectSubjectVO> list);
    Integer updateById(Long id, MemberCollectSubjectVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

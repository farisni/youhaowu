package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.MemberCollectSubjectQueryDTO;
import com.wheatmall.member.vo.MemberCollectSubjectVO;

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

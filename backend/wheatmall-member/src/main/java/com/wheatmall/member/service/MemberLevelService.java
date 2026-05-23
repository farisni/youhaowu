package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.MemberLevelQueryDTO;
import com.wheatmall.member.vo.MemberLevelVO;

import java.util.List;

public interface MemberLevelService {
    PageData<MemberLevelVO> page(MemberLevelQueryDTO query);
    MemberLevelVO getById(Long id);
    Integer save(MemberLevelVO vo);
    Integer saveBatch(List<MemberLevelVO> list);
    Integer updateById(Long id, MemberLevelVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberCollectSpuQueryDTO;
import com.youhaowu.member.vo.MemberCollectSpuVO;

import java.util.List;

public interface MemberCollectSpuService {
    PageData<MemberCollectSpuVO> page(MemberCollectSpuQueryDTO query);
    MemberCollectSpuVO getById(Long id);
    Integer save(MemberCollectSpuVO vo);
    Integer saveBatch(List<MemberCollectSpuVO> list);
    Integer updateById(Long id, MemberCollectSpuVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

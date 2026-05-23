package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.MemberReceiveAddressQueryDTO;
import com.wheatmall.member.vo.MemberReceiveAddressVO;

import java.util.List;

public interface MemberReceiveAddressService {
    PageData<MemberReceiveAddressVO> page(MemberReceiveAddressQueryDTO query);
    MemberReceiveAddressVO getById(Long id);
    Integer save(MemberReceiveAddressVO vo);
    Integer saveBatch(List<MemberReceiveAddressVO> list);
    Integer updateById(Long id, MemberReceiveAddressVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

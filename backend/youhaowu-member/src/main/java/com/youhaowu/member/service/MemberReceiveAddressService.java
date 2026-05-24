package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberReceiveAddressQueryDTO;
import com.youhaowu.member.vo.MemberReceiveAddressVO;

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

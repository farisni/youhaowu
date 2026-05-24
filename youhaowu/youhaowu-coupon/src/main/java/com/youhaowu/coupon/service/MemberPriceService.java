package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.MemberPriceQueryDTO;
import com.youhaowu.coupon.vo.MemberPriceVO;

import java.util.List;

/**
 * MemberPrice Service
 */
public interface MemberPriceService {
    PageData<MemberPriceVO> page(MemberPriceQueryDTO query);
    MemberPriceVO getById(Long id);
    Integer save(MemberPriceVO vo);
    Integer saveBatch(List<MemberPriceVO> list);
    Integer updateById(Long id, MemberPriceVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

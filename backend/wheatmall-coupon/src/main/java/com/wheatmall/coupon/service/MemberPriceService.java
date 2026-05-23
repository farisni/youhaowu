package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.MemberPriceQueryDTO;
import com.wheatmall.coupon.vo.MemberPriceVO;

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

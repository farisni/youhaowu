package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponQueryDTO;
import com.youhaowu.coupon.vo.CouponVO;

import java.util.List;

/**
 * Coupon Service
 */
public interface CouponService {
    PageData<CouponVO> page(CouponQueryDTO query);
    CouponVO getById(Long id);
    Integer save(CouponVO vo);
    Integer saveBatch(List<CouponVO> list);
    Integer updateById(Long id, CouponVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

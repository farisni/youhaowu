package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.CouponQueryDTO;
import com.wheatmall.coupon.vo.CouponVO;

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

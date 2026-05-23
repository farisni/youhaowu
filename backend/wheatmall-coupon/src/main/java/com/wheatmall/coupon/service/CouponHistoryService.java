package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.CouponHistoryQueryDTO;
import com.wheatmall.coupon.vo.CouponHistoryVO;

import java.util.List;

/**
 * CouponHistory Service
 */
public interface CouponHistoryService {
    PageData<CouponHistoryVO> page(CouponHistoryQueryDTO query);
    CouponHistoryVO getById(Long id);
    Integer save(CouponHistoryVO vo);
    Integer saveBatch(List<CouponHistoryVO> list);
    Integer updateById(Long id, CouponHistoryVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

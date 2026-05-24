package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponHistoryQueryDTO;
import com.youhaowu.coupon.vo.CouponHistoryVO;

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

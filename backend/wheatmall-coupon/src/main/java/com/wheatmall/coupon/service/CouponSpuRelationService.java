package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.CouponSpuRelationQueryDTO;
import com.wheatmall.coupon.vo.CouponSpuRelationVO;

import java.util.List;

/**
 * CouponSpuRelation Service
 */
public interface CouponSpuRelationService {
    PageData<CouponSpuRelationVO> page(CouponSpuRelationQueryDTO query);
    CouponSpuRelationVO getById(Long id);
    Integer save(CouponSpuRelationVO vo);
    Integer saveBatch(List<CouponSpuRelationVO> list);
    Integer updateById(Long id, CouponSpuRelationVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

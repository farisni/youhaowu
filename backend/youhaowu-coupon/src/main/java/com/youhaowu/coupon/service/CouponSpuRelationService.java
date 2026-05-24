package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponSpuRelationQueryDTO;
import com.youhaowu.coupon.vo.CouponSpuRelationVO;

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

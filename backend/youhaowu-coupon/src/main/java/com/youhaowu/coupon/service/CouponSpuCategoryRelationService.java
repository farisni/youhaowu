package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponSpuCategoryRelationQueryDTO;
import com.youhaowu.coupon.vo.CouponSpuCategoryRelationVO;

import java.util.List;

/**
 * CouponSpuCategoryRelation Service
 */
public interface CouponSpuCategoryRelationService {
    PageData<CouponSpuCategoryRelationVO> page(CouponSpuCategoryRelationQueryDTO query);
    CouponSpuCategoryRelationVO getById(Long id);
    Integer save(CouponSpuCategoryRelationVO vo);
    Integer saveBatch(List<CouponSpuCategoryRelationVO> list);
    Integer updateById(Long id, CouponSpuCategoryRelationVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.CouponSpuCategoryRelationQueryDTO;
import com.wheatmall.coupon.vo.CouponSpuCategoryRelationVO;

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

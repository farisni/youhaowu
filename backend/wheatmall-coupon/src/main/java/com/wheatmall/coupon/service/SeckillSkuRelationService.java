package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SeckillSkuRelationQueryDTO;
import com.wheatmall.coupon.vo.SeckillSkuRelationVO;

import java.util.List;

/**
 * SeckillSkuRelation Service
 */
public interface SeckillSkuRelationService {
    PageData<SeckillSkuRelationVO> page(SeckillSkuRelationQueryDTO query);
    SeckillSkuRelationVO getById(Long id);
    Integer save(SeckillSkuRelationVO vo);
    Integer saveBatch(List<SeckillSkuRelationVO> list);
    Integer updateById(Long id, SeckillSkuRelationVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

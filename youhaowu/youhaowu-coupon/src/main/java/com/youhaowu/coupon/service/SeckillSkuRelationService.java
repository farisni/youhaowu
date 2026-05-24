package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillSkuRelationQueryDTO;
import com.youhaowu.coupon.vo.SeckillSkuRelationVO;

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

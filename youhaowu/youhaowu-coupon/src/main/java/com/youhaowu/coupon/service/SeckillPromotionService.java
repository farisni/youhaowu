package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillPromotionQueryDTO;
import com.youhaowu.coupon.vo.SeckillPromotionVO;

import java.util.List;

/**
 * SeckillPromotion Service
 */
public interface SeckillPromotionService {
    PageData<SeckillPromotionVO> page(SeckillPromotionQueryDTO query);
    SeckillPromotionVO getById(Long id);
    Integer save(SeckillPromotionVO vo);
    Integer saveBatch(List<SeckillPromotionVO> list);
    Integer updateById(Long id, SeckillPromotionVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

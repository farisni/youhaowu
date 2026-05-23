package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SeckillPromotionQueryDTO;
import com.wheatmall.coupon.vo.SeckillPromotionVO;

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

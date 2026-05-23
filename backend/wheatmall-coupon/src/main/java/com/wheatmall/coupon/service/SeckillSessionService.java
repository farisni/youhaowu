package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SeckillSessionQueryDTO;
import com.wheatmall.coupon.vo.SeckillSessionVO;

import java.util.List;

/**
 * SeckillSession Service
 */
public interface SeckillSessionService {
    PageData<SeckillSessionVO> page(SeckillSessionQueryDTO query);
    SeckillSessionVO getById(Long id);
    Integer save(SeckillSessionVO vo);
    Integer saveBatch(List<SeckillSessionVO> list);
    Integer updateById(Long id, SeckillSessionVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

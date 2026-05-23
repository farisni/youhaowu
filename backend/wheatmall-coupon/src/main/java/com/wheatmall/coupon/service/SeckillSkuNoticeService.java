package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SeckillSkuNoticeQueryDTO;
import com.wheatmall.coupon.vo.SeckillSkuNoticeVO;

import java.util.List;

/**
 * SeckillSkuNotice Service
 */
public interface SeckillSkuNoticeService {
    PageData<SeckillSkuNoticeVO> page(SeckillSkuNoticeQueryDTO query);
    SeckillSkuNoticeVO getById(Long id);
    Integer save(SeckillSkuNoticeVO vo);
    Integer saveBatch(List<SeckillSkuNoticeVO> list);
    Integer updateById(Long id, SeckillSkuNoticeVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

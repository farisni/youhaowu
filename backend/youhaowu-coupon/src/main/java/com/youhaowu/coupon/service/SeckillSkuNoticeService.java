package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillSkuNoticeQueryDTO;
import com.youhaowu.coupon.vo.SeckillSkuNoticeVO;

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

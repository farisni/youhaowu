package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.HomeAdvQueryDTO;
import com.youhaowu.coupon.vo.HomeAdvVO;

import java.util.List;

/**
 * HomeAdv Service
 */
public interface HomeAdvService {
    PageData<HomeAdvVO> page(HomeAdvQueryDTO query);
    HomeAdvVO getById(Long id);
    Integer save(HomeAdvVO vo);
    Integer saveBatch(List<HomeAdvVO> list);
    Integer updateById(Long id, HomeAdvVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

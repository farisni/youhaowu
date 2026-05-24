package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.HomeSubjectSpuQueryDTO;
import com.youhaowu.coupon.vo.HomeSubjectSpuVO;

import java.util.List;

/**
 * HomeSubjectSpu Service
 */
public interface HomeSubjectSpuService {
    PageData<HomeSubjectSpuVO> page(HomeSubjectSpuQueryDTO query);
    HomeSubjectSpuVO getById(Long id);
    Integer save(HomeSubjectSpuVO vo);
    Integer saveBatch(List<HomeSubjectSpuVO> list);
    Integer updateById(Long id, HomeSubjectSpuVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

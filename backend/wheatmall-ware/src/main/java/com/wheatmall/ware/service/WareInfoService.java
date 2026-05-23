package com.wheatmall.ware.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.dto.WareInfoQueryDTO;
import com.wheatmall.ware.vo.WareInfoVO;

import java.util.List;

/**
 * WareInfo Service
 */
public interface WareInfoService {

    PageData<WareInfoVO> page(WareInfoQueryDTO query);
    WareInfoVO getById(Long id);
    Integer save(WareInfoVO vo);
    Integer saveBatch(List<WareInfoVO> list);
    Integer updateById(Long id, WareInfoVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

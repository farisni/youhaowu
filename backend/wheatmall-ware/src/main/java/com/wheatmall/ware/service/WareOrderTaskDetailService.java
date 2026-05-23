package com.wheatmall.ware.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.dto.WareOrderTaskDetailQueryDTO;
import com.wheatmall.ware.vo.WareOrderTaskDetailVO;

import java.util.List;

/**
 * WareOrderTaskDetail Service
 */
public interface WareOrderTaskDetailService {

    PageData<WareOrderTaskDetailVO> page(WareOrderTaskDetailQueryDTO query);
    WareOrderTaskDetailVO getById(Long id);
    Integer save(WareOrderTaskDetailVO vo);
    Integer saveBatch(List<WareOrderTaskDetailVO> list);
    Integer updateById(Long id, WareOrderTaskDetailVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

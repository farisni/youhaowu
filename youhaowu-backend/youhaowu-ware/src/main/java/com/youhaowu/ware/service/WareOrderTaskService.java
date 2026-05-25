package com.youhaowu.ware.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.WareOrderTaskQueryDTO;
import com.youhaowu.ware.vo.WareOrderTaskVO;

import java.util.List;

/**
 * WareOrderTask Service
 */
public interface WareOrderTaskService {

    PageData<WareOrderTaskVO> page(WareOrderTaskQueryDTO query);
    WareOrderTaskVO getById(Long id);
    Integer save(WareOrderTaskVO vo);
    Integer saveBatch(List<WareOrderTaskVO> list);
    Integer updateById(Long id, WareOrderTaskVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

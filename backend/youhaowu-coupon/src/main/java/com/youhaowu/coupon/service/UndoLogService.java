package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.UndoLogQueryDTO;
import com.youhaowu.coupon.vo.UndoLogVO;

import java.util.List;

/**
 * UndoLog Service
 */
public interface UndoLogService {
    PageData<UndoLogVO> page(UndoLogQueryDTO query);
    UndoLogVO getById(Long id);
    Integer save(UndoLogVO vo);
    Integer saveBatch(List<UndoLogVO> list);
    Integer updateById(Long id, UndoLogVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

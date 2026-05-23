package com.wheatmall.member.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.dto.UndoLogQueryDTO;
import com.wheatmall.member.vo.UndoLogVO;

import java.util.List;

public interface UndoLogService {
    PageData<UndoLogVO> page(UndoLogQueryDTO query);
    UndoLogVO getById(Long id);
    Integer save(UndoLogVO vo);
    Integer saveBatch(List<UndoLogVO> list);
    Integer updateById(Long id, UndoLogVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

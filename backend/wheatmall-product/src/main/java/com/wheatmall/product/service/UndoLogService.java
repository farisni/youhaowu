package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.UndoLogVO;
import com.wheatmall.common.dto.BaseQueryDTO;

/**
 * UndoLog Service
 */
public interface UndoLogService {

    /** 分页查询 */
    PageData<UndoLogVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    UndoLogVO getById(Long id);

    /** 保存 */
    Integer save(UndoLogVO vo);

    /** 批量保存 */
    Integer saveBatch(java.util.List<UndoLogVO> list);

    /** 根据ID更新 */
    Integer updateById(UndoLogVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
}

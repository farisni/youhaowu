package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.UndoLogVO;
import com.youhaowu.common.dto.BaseQueryDTO;

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

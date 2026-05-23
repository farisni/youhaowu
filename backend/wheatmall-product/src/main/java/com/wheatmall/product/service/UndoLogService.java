package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.UndoLogVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.UndoLogEntity;

/**
 * UndoLog Service
 */
public interface UndoLogService {

    /** 分页查询 */
    PageData<UndoLogVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    UndoLogEntity getById(Long id);

    /** 保存 */
    Integer save(UndoLogEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<UndoLogEntity> list);

    /** 根据ID更新 */
    Integer updateById(UndoLogEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}

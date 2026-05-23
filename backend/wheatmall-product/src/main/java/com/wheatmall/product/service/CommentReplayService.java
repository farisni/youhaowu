package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.CommentReplayVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.CommentReplayEntity;
import java.util.List;

/**
 * CommentReplay Service
 */
public interface CommentReplayService {

    /** 分页查询 */
    PageData<CommentReplayVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    CommentReplayEntity getById(Long id);

    /** 保存 */
    Integer save(CommentReplayEntity entity);

    /** 批量保存 */
    void saveBatch(List<CommentReplayEntity> list);

    /** 根据ID更新 */
    Integer updateById(CommentReplayEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}

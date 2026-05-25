package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.CommentReplayVO;
import com.youhaowu.common.dto.BaseQueryDTO;
import java.util.List;

/**
 * CommentReplay Service
 */
public interface CommentReplayService {

    /** 分页查询 */
    PageData<CommentReplayVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    CommentReplayVO getById(Long id);

    /** 保存 */
    Integer save(CommentReplayVO vo);

    /** 批量保存 */
    Integer saveBatch(List<CommentReplayVO> list);

    /** 根据ID更新 */
    Integer updateById(CommentReplayVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
}

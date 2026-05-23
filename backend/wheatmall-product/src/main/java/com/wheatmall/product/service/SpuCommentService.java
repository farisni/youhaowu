package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuCommentVO;
import com.wheatmall.common.dto.BaseQueryDTO;

/**
 * SpuComment Service
 */
public interface SpuCommentService {

    /** 分页查询 */
    PageData<SpuCommentVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuCommentVO getById(Long id);

    /** 保存 */
    Integer save(SpuCommentVO vo);

    /** 批量保存 */
    Integer saveBatch(java.util.List<SpuCommentVO> list);

    /** 根据ID更新 */
    Integer updateById(SpuCommentVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
}

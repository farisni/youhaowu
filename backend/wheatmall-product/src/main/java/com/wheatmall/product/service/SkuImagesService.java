package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuImagesVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import java.util.List;

/**
 * SkuImages Service
 */
public interface SkuImagesService {

    /** 分页查询 */
    PageData<SkuImagesVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SkuImagesVO getById(Long id);

    /** 保存 */
    Integer save(SkuImagesVO vo);

    /** 批量保存 */
    Integer saveBatch(List<SkuImagesVO> list);

    /** 根据ID更新 */
    Integer updateById(SkuImagesVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
}

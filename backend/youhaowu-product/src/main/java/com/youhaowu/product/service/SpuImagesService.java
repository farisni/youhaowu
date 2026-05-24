package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SpuImagesVO;
import com.youhaowu.common.dto.BaseQueryDTO;
import java.util.List;

/**
 * SpuImages Service
 */
public interface SpuImagesService {

    /** 分页查询 */
    PageData<SpuImagesVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuImagesVO getById(Long id);

    /** 保存 */
    Integer save(SpuImagesVO vo);

    /** 批量保存 */
    Integer saveBatch(List<SpuImagesVO> list);

    /** 根据ID更新 */
    Integer updateById(SpuImagesVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
    Integer saveImages(Long spuId, List<String> images);
}

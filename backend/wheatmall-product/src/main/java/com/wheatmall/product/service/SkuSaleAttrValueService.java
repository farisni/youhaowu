package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuSaleAttrValueVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuSaleAttrValueEntity;
import java.util.List;

/**
 * SkuSaleAttrValue Service
 */
public interface SkuSaleAttrValueService {

    /** 分页查询 */
    PageData<SkuSaleAttrValueVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SkuSaleAttrValueEntity getById(Long id);

    /** 保存 */
    Integer save(SkuSaleAttrValueVO vo);

    /** 批量保存 */
    Integer saveBatch(List<SkuSaleAttrValueVO> list);

    /** 根据ID更新 */
    Integer updateById(SkuSaleAttrValueVO vo);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}

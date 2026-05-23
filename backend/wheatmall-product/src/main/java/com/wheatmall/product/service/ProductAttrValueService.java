package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.ProductAttrValueEntity;
import com.wheatmall.product.vo.ProductAttrValueVO;
import java.util.List;

/**
 * ProductAttrValue Service
 */
public interface ProductAttrValueService {

    /** 分页查询 */
    PageData<ProductAttrValueVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    ProductAttrValueEntity getById(Long id);

    /** 保存 */
    Integer save(ProductAttrValueEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<ProductAttrValueEntity> list);

    /** 根据ID更新 */
    Integer updateById(ProductAttrValueEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
    void saveProductAttr(List<ProductAttrValueEntity> collect);
    List<ProductAttrValueEntity> baseAttrListforspu(Long spuId);
    List<ProductAttrValueVO> listBySpuId(Long spuId);
}

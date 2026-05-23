package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.SkuQueryDTO;
import com.wheatmall.product.vo.SkuInfoVO;

import java.util.List;

/**
 * sku信息 Service
 */
public interface SkuInfoService {

    PageData<SkuInfoVO> page(SkuQueryDTO query);
    SkuInfoVO getVOById(Long id);
    Integer saveSkuInfo(SkuInfoVO vo);
    List<SkuInfoVO> getSkusBySpuId(Long spuId);
    List<SkuInfoVO> listBySpuId(Long spuId);
}

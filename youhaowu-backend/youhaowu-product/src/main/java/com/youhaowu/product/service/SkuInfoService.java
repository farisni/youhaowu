package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.SkuQueryDTO;
import com.youhaowu.product.vo.SkuInfoVO;

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

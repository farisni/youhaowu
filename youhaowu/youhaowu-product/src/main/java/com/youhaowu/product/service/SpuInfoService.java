package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.vo.SpuInfoVO;
import com.youhaowu.product.vo.SpuSaveVO;
import com.youhaowu.product.entity.SpuInfoEntity;

/**
 * spu信息 Service
 */
public interface SpuInfoService {

    PageData<SpuInfoVO> page(BaseQueryDTO query);
    SpuInfoVO getVOById(Long id);
    Integer saveBaseSpuInfo(SpuInfoVO vo);
    Integer saveSupInfo(SpuSaveVO vo);
    Integer up(Long spuId);
}

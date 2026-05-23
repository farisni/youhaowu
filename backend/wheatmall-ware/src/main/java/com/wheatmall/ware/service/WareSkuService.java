package com.wheatmall.ware.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.query.WareSkuQueryDTO;
import com.wheatmall.ware.vo.WareSkuVO;
import com.wheatmall.ware.vo.MergeVo;
import com.wheatmall.ware.vo.PurchaseDoneVo;
import com.wheatmall.common.vo.SkuHasStockVO;

import java.util.List;

/**
 * WareSku Service
 */
public interface WareSkuService {

    List<SkuHasStockVO> getSkuHasStock(List<Long> skuIds);

    PageData<WareSkuVO> page(WareSkuQueryDTO query);
    WareSkuVO getById(Long id);
    Integer save(WareSkuVO vo);
    Integer saveBatch(List<WareSkuVO> list);
    Integer updateById(Long id, WareSkuVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}

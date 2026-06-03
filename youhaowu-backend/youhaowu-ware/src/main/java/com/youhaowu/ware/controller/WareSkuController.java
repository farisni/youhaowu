package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.WareSkuQueryDTO;
import com.youhaowu.ware.service.WareSkuService;
import com.youhaowu.common.vo.SkuHasStockVO;
import com.youhaowu.ware.vo.WareSkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品库存控制器
 */
@RestController
public class WareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    @GetMapping(WareServiceUris.WareSku.PAGE)
    public PageData<WareSkuVO> list(WareSkuQueryDTO query) {
        return wareSkuService.page(query);
    }

    @GetMapping(WareServiceUris.WareSku.INFO)
    public WareSkuVO info(@PathVariable Long id) {
        return wareSkuService.getById(id);
    }

    @PostMapping(WareServiceUris.WareSku.SAVE)
    public Integer save(@RequestBody WareSkuVO vo) {
        return wareSkuService.save(vo);
    }

    @PostMapping(WareServiceUris.WareSku.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody WareSkuVO vo) {
        return wareSkuService.updateById(id, vo);
    }

    @PostMapping(WareServiceUris.WareSku.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return wareSkuService.removeByIds(ids);
    }

    /**
     * 查询 SKU 是否有库存
     */
    @PostMapping(WareServiceUris.WareSku.HAS_STOCK)
    public List<SkuHasStockVO> hasStock(@RequestBody List<Long> skuIds) {
        return wareSkuService.getSkuHasStock(skuIds);
    }
}

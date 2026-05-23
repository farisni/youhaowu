package com.wheatmall.ware.controller;

import com.wheatmall.common.constant.WareServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.ware.dto.WareSkuQueryDTO;
import com.wheatmall.ware.service.WareSkuService;
import com.wheatmall.common.vo.SkuHasStockVO;
import com.wheatmall.ware.vo.WareSkuVO;
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
    public R<PageData<WareSkuVO>> list(WareSkuQueryDTO query) {
        return R.ok(wareSkuService.page(query));
    }

    @GetMapping(WareServiceUris.WareSku.INFO)
    public R<WareSkuVO> info(@PathVariable Long id) {
        return R.ok(wareSkuService.getById(id));
    }

    @PostMapping(WareServiceUris.WareSku.SAVE)
    public R<Integer> save(@RequestBody WareSkuVO vo) {
        return R.ok(wareSkuService.save(vo));
    }

    @PostMapping(WareServiceUris.WareSku.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody WareSkuVO vo) {
        return R.ok(wareSkuService.updateById(id, vo));
    }

    @PostMapping(WareServiceUris.WareSku.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(wareSkuService.removeByIds(ids));
    }

    /**
     * 查询 SKU 是否有库存
     */
    @PostMapping(WareServiceUris.WareSku.HAS_STOCK)
    public R<List<SkuHasStockVO>> hasStock(@RequestBody List<Long> skuIds) {
        return R.ok(wareSkuService.getSkuHasStock(skuIds));
    }
}

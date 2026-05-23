package com.wheatmall.ware.controller;

import com.wheatmall.common.constant.WareServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.ware.dto.PurchaseDetailQueryDTO;
import com.wheatmall.ware.service.PurchaseDetailService;
import com.wheatmall.ware.vo.PurchaseDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PurchaseDetail 控制器
 */
@RestController
public class PurchaseDetailController {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @GetMapping(WareServiceUris.PurchaseDetail.PAGE)
    public R<PageData<PurchaseDetailVO>> list(PurchaseDetailQueryDTO query) {
        return R.ok(purchaseDetailService.page(query));
    }

    @GetMapping(WareServiceUris.PurchaseDetail.INFO)
    public R<PurchaseDetailVO> info(@PathVariable Long id) {
        return R.ok(purchaseDetailService.getById(id));
    }

    @GetMapping(WareServiceUris.PurchaseDetail.SAVE)
    public R<Integer> save(@RequestBody PurchaseDetailVO vo) {
        return R.ok(purchaseDetailService.save(vo));
    }

    @GetMapping(WareServiceUris.PurchaseDetail.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody PurchaseDetailVO vo) {
        return R.ok(purchaseDetailService.updateById(id, vo));
    }

    @GetMapping(WareServiceUris.PurchaseDetail.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(purchaseDetailService.removeByIds(ids));
    }
}

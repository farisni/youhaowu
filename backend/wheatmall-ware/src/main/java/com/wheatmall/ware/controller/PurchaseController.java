package com.wheatmall.ware.controller;

import com.wheatmall.common.constant.WareServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.ware.query.PurchaseQueryDTO;
import com.wheatmall.ware.service.PurchaseService;
import com.wheatmall.ware.vo.MergeVo;
import com.wheatmall.ware.vo.PurchaseDoneVo;
import com.wheatmall.ware.vo.PurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购单控制器
 */
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 分页查询
     */
    @GetMapping(WareServiceUris.Purchase.PAGE)
    public R<PageData<PurchaseVO>> list(PurchaseQueryDTO query) {
        return R.ok(purchaseService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(WareServiceUris.Purchase.INFO)
    public R<PurchaseVO> info(@PathVariable Long id) {
        return R.ok(purchaseService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(WareServiceUris.Purchase.SAVE)
    public R<Integer> save(@RequestBody PurchaseVO vo) {
        return R.ok(purchaseService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(WareServiceUris.Purchase.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody PurchaseVO vo) {
        return R.ok(purchaseService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(WareServiceUris.Purchase.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(purchaseService.removeByIds(ids));
    }

    /**
     * 查询未领取的采购单
     */
    @GetMapping(WareServiceUris.Purchase.UNRECEIVE_LIST)
    public R<PageData<PurchaseVO>> unreceiveList(PurchaseQueryDTO query) {
        return R.ok(purchaseService.queryPageUnreceive(query));
    }

    /**
     * 合并整单
     */
    @PostMapping(WareServiceUris.Purchase.MERGE)
    public R<Void> merge(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    /**
     * 领取采购单
     */
    @PostMapping(WareServiceUris.Purchase.RECEIVED)
    public R<Void> received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);
        return R.ok();
    }

    /**
     * 完成采购单
     */
    @PostMapping(WareServiceUris.Purchase.DONE)
    public R<Void> done(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);
        return R.ok();
    }
}

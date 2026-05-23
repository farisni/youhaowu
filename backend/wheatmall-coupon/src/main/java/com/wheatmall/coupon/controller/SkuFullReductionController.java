package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SkuFullReductionQueryDTO;
import com.wheatmall.coupon.service.SkuFullReductionService;
import com.wheatmall.coupon.vo.SkuFullReductionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SkuFullReduction 控制器
 */
@RestController
public class SkuFullReductionController {

    @Autowired
    private SkuFullReductionService skuFullReductionService;

    @GetMapping(CouponServiceUris.SkuFullReduction.PAGE)
    public R<PageData<SkuFullReductionVO>> page(SkuFullReductionQueryDTO query) {
        return R.ok(skuFullReductionService.page(query));
    }

    @GetMapping(CouponServiceUris.SkuFullReduction.INFO)
    public R<SkuFullReductionVO> info(@PathVariable Long id) {
        return R.ok(skuFullReductionService.getById(id));
    }

    @PostMapping(CouponServiceUris.SkuFullReduction.SAVE)
    public R<Integer> save(@RequestBody SkuFullReductionVO vo) {
        return R.ok(skuFullReductionService.save(vo));
    }

    @PostMapping(CouponServiceUris.SkuFullReduction.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SkuFullReductionVO> list) {
        return R.ok(skuFullReductionService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SkuFullReduction.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SkuFullReductionVO vo) {
        return R.ok(skuFullReductionService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SkuFullReduction.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(skuFullReductionService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SkuFullReduction.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(skuFullReductionService.removeByIds(ids));
    }
}

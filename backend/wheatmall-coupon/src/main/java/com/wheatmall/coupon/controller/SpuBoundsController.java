package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SpuBoundsQueryDTO;
import com.wheatmall.coupon.service.SpuBoundsService;
import com.wheatmall.coupon.vo.SpuBoundsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SpuBounds 控制器
 */
@RestController
public class SpuBoundsController {

    @Autowired
    private SpuBoundsService spuBoundsService;

    @GetMapping(CouponServiceUris.SpuBounds.PAGE)
    public R<PageData<SpuBoundsVO>> page(SpuBoundsQueryDTO query) {
        return R.ok(spuBoundsService.page(query));
    }

    @GetMapping(CouponServiceUris.SpuBounds.INFO)
    public R<SpuBoundsVO> info(@PathVariable Long id) {
        return R.ok(spuBoundsService.getById(id));
    }

    @PostMapping(CouponServiceUris.SpuBounds.SAVE)
    public R<Integer> save(@RequestBody SpuBoundsVO vo) {
        return R.ok(spuBoundsService.save(vo));
    }

    @PostMapping(CouponServiceUris.SpuBounds.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SpuBoundsVO> list) {
        return R.ok(spuBoundsService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SpuBounds.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SpuBoundsVO vo) {
        return R.ok(spuBoundsService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SpuBounds.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(spuBoundsService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SpuBounds.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(spuBoundsService.removeByIds(ids));
    }
}

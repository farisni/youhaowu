package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.CouponQueryDTO;
import com.wheatmall.coupon.service.CouponService;
import com.wheatmall.coupon.vo.CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Coupon 控制器
 */
@RestController
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping(CouponServiceUris.Coupon.PAGE)
    public R<PageData<CouponVO>> page(CouponQueryDTO query) {
        return R.ok(couponService.page(query));
    }

    @GetMapping(CouponServiceUris.Coupon.INFO)
    public R<CouponVO> info(@PathVariable Long id) {
        return R.ok(couponService.getById(id));
    }

    @PostMapping(CouponServiceUris.Coupon.SAVE)
    public R<Integer> save(@RequestBody CouponVO vo) {
        return R.ok(couponService.save(vo));
    }

    @PostMapping(CouponServiceUris.Coupon.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<CouponVO> list) {
        return R.ok(couponService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.Coupon.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody CouponVO vo) {
        return R.ok(couponService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.Coupon.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(couponService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.Coupon.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(couponService.removeByIds(ids));
    }
}

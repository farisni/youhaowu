package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponQueryDTO;
import com.youhaowu.coupon.service.CouponService;
import com.youhaowu.coupon.vo.CouponVO;
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
    public PageData<CouponVO> page(CouponQueryDTO query) {
        return couponService.page(query);
    }

    @GetMapping(CouponServiceUris.Coupon.INFO)
    public CouponVO info(@PathVariable Long id) {
        return couponService.getById(id);
    }

    @PostMapping(CouponServiceUris.Coupon.SAVE)
    public Integer save(@RequestBody CouponVO vo) {
        return couponService.save(vo);
    }

    @PostMapping(CouponServiceUris.Coupon.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<CouponVO> list) {
        return couponService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.Coupon.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody CouponVO vo) {
        return couponService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.Coupon.DELETE)
    public Integer delete(@PathVariable Long id) {
        return couponService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.Coupon.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return couponService.removeByIds(ids);
    }
}

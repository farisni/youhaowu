package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.CouponSpuRelationQueryDTO;
import com.wheatmall.coupon.service.CouponSpuRelationService;
import com.wheatmall.coupon.vo.CouponSpuRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CouponSpuRelation 控制器
 */
@RestController
public class CouponSpuRelationController {

    @Autowired
    private CouponSpuRelationService couponSpuRelationService;

    @GetMapping(CouponServiceUris.CouponSpuRelation.PAGE)
    public R<PageData<CouponSpuRelationVO>> page(CouponSpuRelationQueryDTO query) {
        return R.ok(couponSpuRelationService.page(query));
    }

    @GetMapping(CouponServiceUris.CouponSpuRelation.INFO)
    public R<CouponSpuRelationVO> info(@PathVariable Long id) {
        return R.ok(couponSpuRelationService.getById(id));
    }

    @PostMapping(CouponServiceUris.CouponSpuRelation.SAVE)
    public R<Integer> save(@RequestBody CouponSpuRelationVO vo) {
        return R.ok(couponSpuRelationService.save(vo));
    }

    @PostMapping(CouponServiceUris.CouponSpuRelation.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<CouponSpuRelationVO> list) {
        return R.ok(couponSpuRelationService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.CouponSpuRelation.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody CouponSpuRelationVO vo) {
        return R.ok(couponSpuRelationService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.CouponSpuRelation.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(couponSpuRelationService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.CouponSpuRelation.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(couponSpuRelationService.removeByIds(ids));
    }
}

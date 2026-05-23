package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.CouponSpuCategoryRelationQueryDTO;
import com.wheatmall.coupon.service.CouponSpuCategoryRelationService;
import com.wheatmall.coupon.vo.CouponSpuCategoryRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CouponSpuCategoryRelation 控制器
 */
@RestController
public class CouponSpuCategoryRelationController {

    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    @GetMapping(CouponServiceUris.CouponSpuCategoryRelation.PAGE)
    public R<PageData<CouponSpuCategoryRelationVO>> page(CouponSpuCategoryRelationQueryDTO query) {
        return R.ok(couponSpuCategoryRelationService.page(query));
    }

    @GetMapping(CouponServiceUris.CouponSpuCategoryRelation.INFO)
    public R<CouponSpuCategoryRelationVO> info(@PathVariable Long id) {
        return R.ok(couponSpuCategoryRelationService.getById(id));
    }

    @PostMapping(CouponServiceUris.CouponSpuCategoryRelation.SAVE)
    public R<Integer> save(@RequestBody CouponSpuCategoryRelationVO vo) {
        return R.ok(couponSpuCategoryRelationService.save(vo));
    }

    @PostMapping(CouponServiceUris.CouponSpuCategoryRelation.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<CouponSpuCategoryRelationVO> list) {
        return R.ok(couponSpuCategoryRelationService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.CouponSpuCategoryRelation.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody CouponSpuCategoryRelationVO vo) {
        return R.ok(couponSpuCategoryRelationService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.CouponSpuCategoryRelation.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(couponSpuCategoryRelationService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.CouponSpuCategoryRelation.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(couponSpuCategoryRelationService.removeByIds(ids));
    }
}

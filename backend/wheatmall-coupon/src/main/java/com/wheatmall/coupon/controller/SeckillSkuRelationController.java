package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SeckillSkuRelationQueryDTO;
import com.wheatmall.coupon.service.SeckillSkuRelationService;
import com.wheatmall.coupon.vo.SeckillSkuRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SeckillSkuRelation 控制器
 */
@RestController
public class SeckillSkuRelationController {

    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @GetMapping(CouponServiceUris.SeckillSkuRelation.PAGE)
    public R<PageData<SeckillSkuRelationVO>> page(SeckillSkuRelationQueryDTO query) {
        return R.ok(seckillSkuRelationService.page(query));
    }

    @GetMapping(CouponServiceUris.SeckillSkuRelation.INFO)
    public R<SeckillSkuRelationVO> info(@PathVariable Long id) {
        return R.ok(seckillSkuRelationService.getById(id));
    }

    @PostMapping(CouponServiceUris.SeckillSkuRelation.SAVE)
    public R<Integer> save(@RequestBody SeckillSkuRelationVO vo) {
        return R.ok(seckillSkuRelationService.save(vo));
    }

    @PostMapping(CouponServiceUris.SeckillSkuRelation.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SeckillSkuRelationVO> list) {
        return R.ok(seckillSkuRelationService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SeckillSkuRelation.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SeckillSkuRelationVO vo) {
        return R.ok(seckillSkuRelationService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuRelation.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(seckillSkuRelationService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuRelation.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(seckillSkuRelationService.removeByIds(ids));
    }
}

package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SeckillPromotionQueryDTO;
import com.wheatmall.coupon.service.SeckillPromotionService;
import com.wheatmall.coupon.vo.SeckillPromotionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SeckillPromotion 控制器
 */
@RestController
public class SeckillPromotionController {

    @Autowired
    private SeckillPromotionService seckillPromotionService;

    @GetMapping(CouponServiceUris.SeckillPromotion.PAGE)
    public R<PageData<SeckillPromotionVO>> page(SeckillPromotionQueryDTO query) {
        return R.ok(seckillPromotionService.page(query));
    }

    @GetMapping(CouponServiceUris.SeckillPromotion.INFO)
    public R<SeckillPromotionVO> info(@PathVariable Long id) {
        return R.ok(seckillPromotionService.getById(id));
    }

    @PostMapping(CouponServiceUris.SeckillPromotion.SAVE)
    public R<Integer> save(@RequestBody SeckillPromotionVO vo) {
        return R.ok(seckillPromotionService.save(vo));
    }

    @PostMapping(CouponServiceUris.SeckillPromotion.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SeckillPromotionVO> list) {
        return R.ok(seckillPromotionService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SeckillPromotion.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SeckillPromotionVO vo) {
        return R.ok(seckillPromotionService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SeckillPromotion.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(seckillPromotionService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SeckillPromotion.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(seckillPromotionService.removeByIds(ids));
    }
}

package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillPromotionQueryDTO;
import com.youhaowu.coupon.service.SeckillPromotionService;
import com.youhaowu.coupon.vo.SeckillPromotionVO;
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
    public PageData<SeckillPromotionVO> page(SeckillPromotionQueryDTO query) {
        return seckillPromotionService.page(query);
    }

    @GetMapping(CouponServiceUris.SeckillPromotion.INFO)
    public SeckillPromotionVO info(@PathVariable Long id) {
        return seckillPromotionService.getById(id);
    }

    @PostMapping(CouponServiceUris.SeckillPromotion.SAVE)
    public Integer save(@RequestBody SeckillPromotionVO vo) {
        return seckillPromotionService.save(vo);
    }

    @PostMapping(CouponServiceUris.SeckillPromotion.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SeckillPromotionVO> list) {
        return seckillPromotionService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SeckillPromotion.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SeckillPromotionVO vo) {
        return seckillPromotionService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SeckillPromotion.DELETE)
    public Integer delete(@PathVariable Long id) {
        return seckillPromotionService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SeckillPromotion.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return seckillPromotionService.removeByIds(ids);
    }
}

package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillSkuRelationQueryDTO;
import com.youhaowu.coupon.service.SeckillSkuRelationService;
import com.youhaowu.coupon.vo.SeckillSkuRelationVO;
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
    public PageData<SeckillSkuRelationVO> page(SeckillSkuRelationQueryDTO query) {
        return seckillSkuRelationService.page(query);
    }

    @GetMapping(CouponServiceUris.SeckillSkuRelation.INFO)
    public SeckillSkuRelationVO info(@PathVariable Long id) {
        return seckillSkuRelationService.getById(id);
    }

    @PostMapping(CouponServiceUris.SeckillSkuRelation.SAVE)
    public Integer save(@RequestBody SeckillSkuRelationVO vo) {
        return seckillSkuRelationService.save(vo);
    }

    @PostMapping(CouponServiceUris.SeckillSkuRelation.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SeckillSkuRelationVO> list) {
        return seckillSkuRelationService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SeckillSkuRelation.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SeckillSkuRelationVO vo) {
        return seckillSkuRelationService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuRelation.DELETE)
    public Integer delete(@PathVariable Long id) {
        return seckillSkuRelationService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuRelation.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return seckillSkuRelationService.removeByIds(ids);
    }
}

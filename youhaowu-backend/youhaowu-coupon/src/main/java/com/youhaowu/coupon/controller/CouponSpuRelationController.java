package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponSpuRelationQueryDTO;
import com.youhaowu.coupon.service.CouponSpuRelationService;
import com.youhaowu.coupon.vo.CouponSpuRelationVO;
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
    public PageData<CouponSpuRelationVO> page(CouponSpuRelationQueryDTO query) {
        return couponSpuRelationService.page(query);
    }

    @GetMapping(CouponServiceUris.CouponSpuRelation.INFO)
    public CouponSpuRelationVO info(@PathVariable Long id) {
        return couponSpuRelationService.getById(id);
    }

    @PostMapping(CouponServiceUris.CouponSpuRelation.SAVE)
    public Integer save(@RequestBody CouponSpuRelationVO vo) {
        return couponSpuRelationService.save(vo);
    }

    @PostMapping(CouponServiceUris.CouponSpuRelation.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<CouponSpuRelationVO> list) {
        return couponSpuRelationService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.CouponSpuRelation.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody CouponSpuRelationVO vo) {
        return couponSpuRelationService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.CouponSpuRelation.DELETE)
    public Integer delete(@PathVariable Long id) {
        return couponSpuRelationService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.CouponSpuRelation.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return couponSpuRelationService.removeByIds(ids);
    }
}

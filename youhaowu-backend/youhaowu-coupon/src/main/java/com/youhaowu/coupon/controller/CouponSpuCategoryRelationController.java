package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.CouponSpuCategoryRelationQueryDTO;
import com.youhaowu.coupon.service.CouponSpuCategoryRelationService;
import com.youhaowu.coupon.vo.CouponSpuCategoryRelationVO;
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
    public PageData<CouponSpuCategoryRelationVO> page(CouponSpuCategoryRelationQueryDTO query) {
        return couponSpuCategoryRelationService.page(query);
    }

    @GetMapping(CouponServiceUris.CouponSpuCategoryRelation.INFO)
    public CouponSpuCategoryRelationVO info(@PathVariable Long id) {
        return couponSpuCategoryRelationService.getById(id);
    }

    @PostMapping(CouponServiceUris.CouponSpuCategoryRelation.SAVE)
    public Integer save(@RequestBody CouponSpuCategoryRelationVO vo) {
        return couponSpuCategoryRelationService.save(vo);
    }

    @PostMapping(CouponServiceUris.CouponSpuCategoryRelation.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<CouponSpuCategoryRelationVO> list) {
        return couponSpuCategoryRelationService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.CouponSpuCategoryRelation.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody CouponSpuCategoryRelationVO vo) {
        return couponSpuCategoryRelationService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.CouponSpuCategoryRelation.DELETE)
    public Integer delete(@PathVariable Long id) {
        return couponSpuCategoryRelationService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.CouponSpuCategoryRelation.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return couponSpuCategoryRelationService.removeByIds(ids);
    }
}

package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SkuFullReductionQueryDTO;
import com.youhaowu.coupon.service.SkuFullReductionService;
import com.youhaowu.coupon.vo.SkuFullReductionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SkuFullReduction 控制器
 */
@RestController
public class SkuFullReductionController {

    @Autowired
    private SkuFullReductionService skuFullReductionService;

    @GetMapping(CouponServiceUris.SkuFullReduction.PAGE)
    public PageData<SkuFullReductionVO> page(SkuFullReductionQueryDTO query) {
        return skuFullReductionService.page(query);
    }

    @GetMapping(CouponServiceUris.SkuFullReduction.INFO)
    public SkuFullReductionVO info(@PathVariable Long id) {
        return skuFullReductionService.getById(id);
    }

    @PostMapping(CouponServiceUris.SkuFullReduction.SAVE)
    public Integer save(@RequestBody SkuFullReductionVO vo) {
        return skuFullReductionService.save(vo);
    }

    @PostMapping(CouponServiceUris.SkuFullReduction.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SkuFullReductionVO> list) {
        return skuFullReductionService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SkuFullReduction.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SkuFullReductionVO vo) {
        return skuFullReductionService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SkuFullReduction.DELETE)
    public Integer delete(@PathVariable Long id) {
        return skuFullReductionService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SkuFullReduction.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return skuFullReductionService.removeByIds(ids);
    }
}

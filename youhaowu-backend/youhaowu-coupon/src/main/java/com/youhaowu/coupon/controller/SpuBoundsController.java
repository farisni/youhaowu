package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SpuBoundsQueryDTO;
import com.youhaowu.coupon.service.SpuBoundsService;
import com.youhaowu.coupon.vo.SpuBoundsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SpuBounds 控制器
 */
@RestController
public class SpuBoundsController {

    @Autowired
    private SpuBoundsService spuBoundsService;

    @GetMapping(CouponServiceUris.SpuBounds.PAGE)
    public PageData<SpuBoundsVO> page(SpuBoundsQueryDTO query) {
        return spuBoundsService.page(query);
    }

    @GetMapping(CouponServiceUris.SpuBounds.INFO)
    public SpuBoundsVO info(@PathVariable Long id) {
        return spuBoundsService.getById(id);
    }

    @PostMapping(CouponServiceUris.SpuBounds.SAVE)
    public Integer save(@RequestBody SpuBoundsVO vo) {
        return spuBoundsService.save(vo);
    }

    @PostMapping(CouponServiceUris.SpuBounds.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SpuBoundsVO> list) {
        return spuBoundsService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SpuBounds.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SpuBoundsVO vo) {
        return spuBoundsService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SpuBounds.DELETE)
    public Integer delete(@PathVariable Long id) {
        return spuBoundsService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SpuBounds.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return spuBoundsService.removeByIds(ids);
    }
}

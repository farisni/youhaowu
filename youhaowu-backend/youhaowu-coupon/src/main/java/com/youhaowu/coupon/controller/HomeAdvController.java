package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.HomeAdvQueryDTO;
import com.youhaowu.coupon.service.HomeAdvService;
import com.youhaowu.coupon.vo.HomeAdvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HomeAdv 控制器
 */
@RestController
public class HomeAdvController {

    @Autowired
    private HomeAdvService homeAdvService;

    @GetMapping(CouponServiceUris.HomeAdv.PAGE)
    public PageData<HomeAdvVO> page(HomeAdvQueryDTO query) {
        return homeAdvService.page(query);
    }

    @GetMapping(CouponServiceUris.HomeAdv.INFO)
    public HomeAdvVO info(@PathVariable Long id) {
        return homeAdvService.getById(id);
    }

    @PostMapping(CouponServiceUris.HomeAdv.SAVE)
    public Integer save(@RequestBody HomeAdvVO vo) {
        return homeAdvService.save(vo);
    }

    @PostMapping(CouponServiceUris.HomeAdv.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<HomeAdvVO> list) {
        return homeAdvService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.HomeAdv.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody HomeAdvVO vo) {
        return homeAdvService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.HomeAdv.DELETE)
    public Integer delete(@PathVariable Long id) {
        return homeAdvService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.HomeAdv.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return homeAdvService.removeByIds(ids);
    }
}

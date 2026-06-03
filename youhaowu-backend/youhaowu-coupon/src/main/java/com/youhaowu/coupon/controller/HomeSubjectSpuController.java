package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.HomeSubjectSpuQueryDTO;
import com.youhaowu.coupon.service.HomeSubjectSpuService;
import com.youhaowu.coupon.vo.HomeSubjectSpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HomeSubjectSpu 控制器
 */
@RestController
public class HomeSubjectSpuController {

    @Autowired
    private HomeSubjectSpuService homeSubjectSpuService;

    @GetMapping(CouponServiceUris.HomeSubjectSpu.PAGE)
    public PageData<HomeSubjectSpuVO> page(HomeSubjectSpuQueryDTO query) {
        return homeSubjectSpuService.page(query);
    }

    @GetMapping(CouponServiceUris.HomeSubjectSpu.INFO)
    public HomeSubjectSpuVO info(@PathVariable Long id) {
        return homeSubjectSpuService.getById(id);
    }

    @PostMapping(CouponServiceUris.HomeSubjectSpu.SAVE)
    public Integer save(@RequestBody HomeSubjectSpuVO vo) {
        return homeSubjectSpuService.save(vo);
    }

    @PostMapping(CouponServiceUris.HomeSubjectSpu.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<HomeSubjectSpuVO> list) {
        return homeSubjectSpuService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.HomeSubjectSpu.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody HomeSubjectSpuVO vo) {
        return homeSubjectSpuService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.HomeSubjectSpu.DELETE)
    public Integer delete(@PathVariable Long id) {
        return homeSubjectSpuService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.HomeSubjectSpu.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return homeSubjectSpuService.removeByIds(ids);
    }
}

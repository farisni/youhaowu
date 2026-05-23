package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.query.OrderSettingQueryDTO;
import com.wheatmall.order.service.OrderSettingService;
import com.wheatmall.order.vo.OrderSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderSetting 控制器
 */
@RestController
public class OrderSettingController {

    @Autowired
    private OrderSettingService orderSettingService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.OrderSetting.PAGE)
    public R<PageData<OrderSettingVO>> list(OrderSettingQueryDTO query) {
        return R.ok(orderSettingService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderSetting.INFO)
    public R<OrderSettingVO> info(@PathVariable Long id) {
        return R.ok(orderSettingService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderSetting.SAVE)
    public R<Integer> save(@RequestBody OrderSettingVO vo) {
        return R.ok(orderSettingService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderSetting.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderSettingVO vo) {
        return R.ok(orderSettingService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderSetting.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderSettingService.removeByIds(ids));
    }
}

package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.order.dto.OrderSettingQueryDTO;
import com.youhaowu.order.service.OrderSettingService;
import com.youhaowu.order.vo.OrderSettingVO;
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

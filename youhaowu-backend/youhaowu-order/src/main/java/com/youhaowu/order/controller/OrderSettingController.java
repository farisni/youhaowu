package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<OrderSettingVO> list(OrderSettingQueryDTO query) {
        return orderSettingService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderSetting.INFO)
    public OrderSettingVO info(@PathVariable Long id) {
        return orderSettingService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderSetting.SAVE)
    public Integer save(@RequestBody OrderSettingVO vo) {
        return orderSettingService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderSetting.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderSettingVO vo) {
        return orderSettingService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderSetting.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderSettingService.removeByIds(ids);
    }
}

package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderQueryDTO;
import com.youhaowu.order.service.OrderService;
import com.youhaowu.order.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order 控制器
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.Order.PAGE)
    public PageData<OrderVO> list(OrderQueryDTO query) {
        return orderService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.Order.INFO)
    public OrderVO info(@PathVariable Long id) {
        return orderService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.Order.SAVE)
    public Integer save(@RequestBody OrderVO vo) {
        return orderService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.Order.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderVO vo) {
        return orderService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.Order.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderService.removeByIds(ids);
    }
}

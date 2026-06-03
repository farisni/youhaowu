package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderItemQueryDTO;
import com.youhaowu.order.service.OrderItemService;
import com.youhaowu.order.vo.OrderItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderItem 控制器
 */
@RestController
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.OrderItem.PAGE)
    public PageData<OrderItemVO> list(OrderItemQueryDTO query) {
        return orderItemService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderItem.INFO)
    public OrderItemVO info(@PathVariable Long id) {
        return orderItemService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderItem.SAVE)
    public Integer save(@RequestBody OrderItemVO vo) {
        return orderItemService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderItem.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderItemVO vo) {
        return orderItemService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderItem.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderItemService.removeByIds(ids);
    }
}

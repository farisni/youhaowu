package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
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
    public R<PageData<OrderItemVO>> list(OrderItemQueryDTO query) {
        return R.ok(orderItemService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderItem.INFO)
    public R<OrderItemVO> info(@PathVariable Long id) {
        return R.ok(orderItemService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderItem.SAVE)
    public R<Integer> save(@RequestBody OrderItemVO vo) {
        return R.ok(orderItemService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderItem.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderItemVO vo) {
        return R.ok(orderItemService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderItem.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderItemService.removeByIds(ids));
    }
}

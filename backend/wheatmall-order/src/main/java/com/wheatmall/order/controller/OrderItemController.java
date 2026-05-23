package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.query.OrderItemQueryDTO;
import com.wheatmall.order.service.OrderItemService;
import com.wheatmall.order.vo.OrderItemVO;
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

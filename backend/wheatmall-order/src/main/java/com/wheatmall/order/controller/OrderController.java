package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.dto.OrderQueryDTO;
import com.wheatmall.order.service.OrderService;
import com.wheatmall.order.vo.OrderVO;
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
    public R<PageData<OrderVO>> list(OrderQueryDTO query) {
        return R.ok(orderService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.Order.INFO)
    public R<OrderVO> info(@PathVariable Long id) {
        return R.ok(orderService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.Order.SAVE)
    public R<Integer> save(@RequestBody OrderVO vo) {
        return R.ok(orderService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.Order.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderVO vo) {
        return R.ok(orderService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.Order.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderService.removeByIds(ids));
    }
}

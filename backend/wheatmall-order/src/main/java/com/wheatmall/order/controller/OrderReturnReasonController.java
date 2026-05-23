package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.query.OrderReturnReasonQueryDTO;
import com.wheatmall.order.service.OrderReturnReasonService;
import com.wheatmall.order.vo.OrderReturnReasonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderReturnReason 控制器
 */
@RestController
public class OrderReturnReasonController {

    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.OrderReturnReason.PAGE)
    public R<PageData<OrderReturnReasonVO>> list(OrderReturnReasonQueryDTO query) {
        return R.ok(orderReturnReasonService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderReturnReason.INFO)
    public R<OrderReturnReasonVO> info(@PathVariable Long id) {
        return R.ok(orderReturnReasonService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.SAVE)
    public R<Integer> save(@RequestBody OrderReturnReasonVO vo) {
        return R.ok(orderReturnReasonService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderReturnReasonVO vo) {
        return R.ok(orderReturnReasonService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderReturnReasonService.removeByIds(ids));
    }
}

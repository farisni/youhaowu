package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.order.dto.OrderOperateHistoryQueryDTO;
import com.youhaowu.order.service.OrderOperateHistoryService;
import com.youhaowu.order.vo.OrderOperateHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderOperateHistory 控制器
 */
@RestController
public class OrderOperateHistoryController {

    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.OrderOperateHistory.PAGE)
    public R<PageData<OrderOperateHistoryVO>> list(OrderOperateHistoryQueryDTO query) {
        return R.ok(orderOperateHistoryService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderOperateHistory.INFO)
    public R<OrderOperateHistoryVO> info(@PathVariable Long id) {
        return R.ok(orderOperateHistoryService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.SAVE)
    public R<Integer> save(@RequestBody OrderOperateHistoryVO vo) {
        return R.ok(orderOperateHistoryService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderOperateHistoryVO vo) {
        return R.ok(orderOperateHistoryService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderOperateHistoryService.removeByIds(ids));
    }
}

package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<OrderOperateHistoryVO> list(OrderOperateHistoryQueryDTO query) {
        return orderOperateHistoryService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderOperateHistory.INFO)
    public OrderOperateHistoryVO info(@PathVariable Long id) {
        return orderOperateHistoryService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.SAVE)
    public Integer save(@RequestBody OrderOperateHistoryVO vo) {
        return orderOperateHistoryService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderOperateHistoryVO vo) {
        return orderOperateHistoryService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderOperateHistory.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderOperateHistoryService.removeByIds(ids);
    }
}

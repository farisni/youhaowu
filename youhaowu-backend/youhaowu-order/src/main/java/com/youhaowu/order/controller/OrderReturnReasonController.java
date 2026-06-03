package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderReturnReasonQueryDTO;
import com.youhaowu.order.service.OrderReturnReasonService;
import com.youhaowu.order.vo.OrderReturnReasonVO;
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
    public PageData<OrderReturnReasonVO> list(OrderReturnReasonQueryDTO query) {
        return orderReturnReasonService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderReturnReason.INFO)
    public OrderReturnReasonVO info(@PathVariable Long id) {
        return orderReturnReasonService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.SAVE)
    public Integer save(@RequestBody OrderReturnReasonVO vo) {
        return orderReturnReasonService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderReturnReasonVO vo) {
        return orderReturnReasonService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderReturnReason.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderReturnReasonService.removeByIds(ids);
    }
}

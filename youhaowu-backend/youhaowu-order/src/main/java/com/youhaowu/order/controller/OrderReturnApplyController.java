package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderReturnApplyQueryDTO;
import com.youhaowu.order.service.OrderReturnApplyService;
import com.youhaowu.order.vo.OrderReturnApplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderReturnApply 控制器
 */
@RestController
public class OrderReturnApplyController {

    @Autowired
    private OrderReturnApplyService orderReturnApplyService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.OrderReturnApply.PAGE)
    public PageData<OrderReturnApplyVO> list(OrderReturnApplyQueryDTO query) {
        return orderReturnApplyService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderReturnApply.INFO)
    public OrderReturnApplyVO info(@PathVariable Long id) {
        return orderReturnApplyService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.SAVE)
    public Integer save(@RequestBody OrderReturnApplyVO vo) {
        return orderReturnApplyService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody OrderReturnApplyVO vo) {
        return orderReturnApplyService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return orderReturnApplyService.removeByIds(ids);
    }
}

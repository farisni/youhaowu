package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.dto.OrderReturnApplyQueryDTO;
import com.wheatmall.order.service.OrderReturnApplyService;
import com.wheatmall.order.vo.OrderReturnApplyVO;
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
    public R<PageData<OrderReturnApplyVO>> list(OrderReturnApplyQueryDTO query) {
        return R.ok(orderReturnApplyService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.OrderReturnApply.INFO)
    public R<OrderReturnApplyVO> info(@PathVariable Long id) {
        return R.ok(orderReturnApplyService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.SAVE)
    public R<Integer> save(@RequestBody OrderReturnApplyVO vo) {
        return R.ok(orderReturnApplyService.save(vo));
    }

    /**
     * 更新
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody OrderReturnApplyVO vo) {
        return R.ok(orderReturnApplyService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @PostMapping(OrderServiceUris.OrderReturnApply.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(orderReturnApplyService.removeByIds(ids));
    }
}

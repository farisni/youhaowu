package com.wheatmall.order.controller;

import com.wheatmall.common.constant.OrderServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.order.query.RefundInfoQueryDTO;
import com.wheatmall.order.service.RefundInfoService;
import com.wheatmall.order.vo.RefundInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * RefundInfo 控制器
 */
@RestController
public class RefundInfoController {

    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.RefundInfo.PAGE)
    public R<PageData<RefundInfoVO>> list(RefundInfoQueryDTO query) {
        return R.ok(refundInfoService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.RefundInfo.INFO)
    public R<RefundInfoVO> info(@PathVariable Long id) {
        return R.ok(refundInfoService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.RefundInfo.SAVE)
    public R<Integer> save(@RequestBody RefundInfoVO vo) {
        return R.ok(refundInfoService.save(vo));
    }


}

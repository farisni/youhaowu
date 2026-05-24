package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.order.dto.RefundInfoQueryDTO;
import com.youhaowu.order.service.RefundInfoService;
import com.youhaowu.order.vo.RefundInfoVO;
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

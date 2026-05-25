package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.order.dto.PaymentInfoQueryDTO;
import com.youhaowu.order.service.PaymentInfoService;
import com.youhaowu.order.vo.PaymentInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PaymentInfo 控制器
 */
@RestController
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.PaymentInfo.PAGE)
    public R<PageData<PaymentInfoVO>> list(PaymentInfoQueryDTO query) {
        return R.ok(paymentInfoService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.PaymentInfo.INFO)
    public R<PaymentInfoVO> info(@PathVariable Long id) {
        return R.ok(paymentInfoService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.PaymentInfo.SAVE)
    public R<Integer> save(@RequestBody PaymentInfoVO vo) {
        return R.ok(paymentInfoService.save(vo));
    }


}

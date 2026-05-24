package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.order.dto.MqMessageQueryDTO;
import com.youhaowu.order.service.MqMessageService;
import com.youhaowu.order.vo.MqMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * MqMessage 控制器
 */
@RestController
public class MqMessageController {

    @Autowired
    private MqMessageService mqMessageService;

    /**
     * 分页查询
     */
    @GetMapping(OrderServiceUris.MqMessage.PAGE)
    public R<PageData<MqMessageVO>> list(MqMessageQueryDTO query) {
        return R.ok(mqMessageService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.MqMessage.INFO)
    public R<MqMessageVO> info(@PathVariable Long id) {
        return R.ok(mqMessageService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.MqMessage.SAVE)
    public R<Integer> save(@RequestBody MqMessageVO vo) {
        return R.ok(mqMessageService.save(vo));
    }


}

package com.youhaowu.order.controller;

import com.youhaowu.common.constant.OrderServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<MqMessageVO> list(MqMessageQueryDTO query) {
        return mqMessageService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(OrderServiceUris.MqMessage.INFO)
    public MqMessageVO info(@PathVariable Long id) {
        return mqMessageService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(OrderServiceUris.MqMessage.SAVE)
    public Integer save(@RequestBody MqMessageVO vo) {
        return mqMessageService.save(vo);
    }


}

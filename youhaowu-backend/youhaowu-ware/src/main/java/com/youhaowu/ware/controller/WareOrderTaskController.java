package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.WareOrderTaskQueryDTO;
import com.youhaowu.ware.service.WareOrderTaskService;
import com.youhaowu.ware.vo.WareOrderTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WareOrderTask 控制器
 */
@RestController
public class WareOrderTaskController {

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @GetMapping(WareServiceUris.WareOrderTask.PAGE)
    public PageData<WareOrderTaskVO> list(WareOrderTaskQueryDTO query) {
        return wareOrderTaskService.page(query);
    }

    @GetMapping(WareServiceUris.WareOrderTask.INFO)
    public WareOrderTaskVO info(@PathVariable Long id) {
        return wareOrderTaskService.getById(id);
    }

    @GetMapping(WareServiceUris.WareOrderTask.SAVE)
    public Integer save(@RequestBody WareOrderTaskVO vo) {
        return wareOrderTaskService.save(vo);
    }

    @GetMapping(WareServiceUris.WareOrderTask.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody WareOrderTaskVO vo) {
        return wareOrderTaskService.updateById(id, vo);
    }

    @GetMapping(WareServiceUris.WareOrderTask.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return wareOrderTaskService.removeByIds(ids);
    }
}

package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
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
    public R<PageData<WareOrderTaskVO>> list(WareOrderTaskQueryDTO query) {
        return R.ok(wareOrderTaskService.page(query));
    }

    @GetMapping(WareServiceUris.WareOrderTask.INFO)
    public R<WareOrderTaskVO> info(@PathVariable Long id) {
        return R.ok(wareOrderTaskService.getById(id));
    }

    @GetMapping(WareServiceUris.WareOrderTask.SAVE)
    public R<Integer> save(@RequestBody WareOrderTaskVO vo) {
        return R.ok(wareOrderTaskService.save(vo));
    }

    @GetMapping(WareServiceUris.WareOrderTask.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody WareOrderTaskVO vo) {
        return R.ok(wareOrderTaskService.updateById(id, vo));
    }

    @GetMapping(WareServiceUris.WareOrderTask.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(wareOrderTaskService.removeByIds(ids));
    }
}

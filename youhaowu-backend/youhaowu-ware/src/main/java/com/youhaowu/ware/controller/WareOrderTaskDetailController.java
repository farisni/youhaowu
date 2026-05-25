package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.ware.dto.WareOrderTaskDetailQueryDTO;
import com.youhaowu.ware.service.WareOrderTaskDetailService;
import com.youhaowu.ware.vo.WareOrderTaskDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WareOrderTaskDetail 控制器
 */
@RestController
public class WareOrderTaskDetailController {

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @GetMapping(WareServiceUris.WareOrderTaskDetail.PAGE)
    public R<PageData<WareOrderTaskDetailVO>> list(WareOrderTaskDetailQueryDTO query) {
        return R.ok(wareOrderTaskDetailService.page(query));
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.INFO)
    public R<WareOrderTaskDetailVO> info(@PathVariable Long id) {
        return R.ok(wareOrderTaskDetailService.getById(id));
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.SAVE)
    public R<Integer> save(@RequestBody WareOrderTaskDetailVO vo) {
        return R.ok(wareOrderTaskDetailService.save(vo));
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody WareOrderTaskDetailVO vo) {
        return R.ok(wareOrderTaskDetailService.updateById(id, vo));
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(wareOrderTaskDetailService.removeByIds(ids));
    }
}

package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.cms.dto.BannerQueryDTO;
import com.youhaowu.cms.dto.BannerSaveDTO;
import com.youhaowu.cms.service.BannerService;
import com.youhaowu.cms.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping(CmsServiceUris.CmsBanner.LIST)
    public R<List<BannerVO>> list() {
        return R.ok(bannerService.listEnabled());
    }

    @GetMapping(CmsServiceUris.CmsBanner.INFO)
    public R<BannerVO> info(@PathVariable Long id) {
        return R.ok(bannerService.getById(id));
    }

    @PostMapping(CmsServiceUris.CmsBanner.PAGE)
    public R<PageData<BannerVO>> page(@RequestBody BannerQueryDTO query) {
        return R.ok(bannerService.page(query));
    }

    @PostMapping(CmsServiceUris.CmsBanner.SAVE)
    public R<Integer> save(@RequestBody BannerSaveDTO dto) {
        return R.ok(bannerService.save(dto));
    }

    @PostMapping(CmsServiceUris.CmsBanner.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody BannerSaveDTO dto) {
        return R.ok(bannerService.update(id, dto));
    }

    @PostMapping(CmsServiceUris.CmsBanner.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(bannerService.deleteBatch(ids));
    }
}

package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public List<BannerVO> list() {
        return bannerService.listEnabled();
    }

    @GetMapping(CmsServiceUris.CmsBanner.INFO)
    public BannerVO info(@PathVariable Long id) {
        return bannerService.getById(id);
    }

    @PostMapping(CmsServiceUris.CmsBanner.PAGE)
    public PageData<BannerVO> page(@RequestBody BannerQueryDTO query) {
        return bannerService.page(query);
    }

    @PostMapping(CmsServiceUris.CmsBanner.SAVE)
    public Integer save(@RequestBody BannerSaveDTO dto) {
        return bannerService.save(dto);
    }

    @PostMapping(CmsServiceUris.CmsBanner.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody BannerSaveDTO dto) {
        return bannerService.update(id, dto);
    }

    @PostMapping(CmsServiceUris.CmsBanner.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return bannerService.deleteBatch(ids);
    }
}

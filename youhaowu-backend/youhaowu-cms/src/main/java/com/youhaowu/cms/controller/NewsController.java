package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.NewsQueryDTO;
import com.youhaowu.cms.dto.NewsSaveDTO;
import com.youhaowu.cms.service.NewsService;
import com.youhaowu.cms.vo.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping(CmsServiceUris.CmsNews.LIST)
    public List<NewsVO> list() {
        return newsService.listEnabled();
    }

    @GetMapping(CmsServiceUris.CmsNews.INFO)
    public NewsVO info(@PathVariable Long id) {
        return newsService.getById(id);
    }

    @PostMapping(CmsServiceUris.CmsNews.PAGE)
    public PageData<NewsVO> page(@RequestBody NewsQueryDTO query) {
        return newsService.page(query);
    }

    @PostMapping(CmsServiceUris.CmsNews.SAVE)
    public Integer save(@RequestBody NewsSaveDTO dto) {
        return newsService.save(dto);
    }

    @PostMapping(CmsServiceUris.CmsNews.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody NewsSaveDTO dto) {
        return newsService.update(id, dto);
    }

    @PostMapping(CmsServiceUris.CmsNews.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return newsService.deleteBatch(ids);
    }
}

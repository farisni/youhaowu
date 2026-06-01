package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
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
    public R<List<NewsVO>> list() {
        return R.ok(newsService.listEnabled());
    }

    @GetMapping(CmsServiceUris.CmsNews.INFO)
    public R<NewsVO> info(@PathVariable Long id) {
        return R.ok(newsService.getById(id));
    }

    @PostMapping(CmsServiceUris.CmsNews.PAGE)
    public R<PageData<NewsVO>> page(@RequestBody NewsQueryDTO query) {
        return R.ok(newsService.page(query));
    }

    @PostMapping(CmsServiceUris.CmsNews.SAVE)
    public R<Integer> save(@RequestBody NewsSaveDTO dto) {
        return R.ok(newsService.save(dto));
    }

    @PostMapping(CmsServiceUris.CmsNews.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody NewsSaveDTO dto) {
        return R.ok(newsService.update(id, dto));
    }

    @PostMapping(CmsServiceUris.CmsNews.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(newsService.deleteBatch(ids));
    }
}

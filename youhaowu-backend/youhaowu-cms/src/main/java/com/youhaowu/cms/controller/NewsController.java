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

    @PostMapping(CmsServiceUris.CmsNews.PAGE)
    public R<PageData<NewsVO>> page(@RequestBody NewsQueryDTO query) {
        return R.ok(newsService.page(query));
    }

    @PostMapping(CmsServiceUris.CmsNews.SAVE)
    public R<Void> save(@RequestBody NewsSaveDTO dto) {
        newsService.save(dto);
        return R.ok();
    }

    @PostMapping(CmsServiceUris.CmsNews.UPDATE)
    public R<Void> update(@PathVariable Long id, @RequestBody NewsSaveDTO dto) {
        newsService.update(id, dto);
        return R.ok();
    }

    @PostMapping(CmsServiceUris.CmsNews.DELETE)
    public R<Void> delete(@RequestBody List<Long> ids) {
        newsService.deleteBatch(ids);
        return R.ok();
    }
}

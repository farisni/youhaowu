package com.youhaowu.search.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.youhaowu.search.service.MallSearchService;
import com.youhaowu.search.vo.SearchParam;
import com.youhaowu.search.vo.SearchResult;

@Controller
public class SearchController {
    @Autowired
    private MallSearchService mallSearchService;

    /**
     * 自动将页面提交过来的所有请求参数封装成我们指定的对象
     *
     * @param param   参数
     * @param model   模型
     * @param request 请求
     * @return {@link String}
     */
    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());

        //1、根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result",result);

        return "list";
    }


}

package com.wheatmall.search.service.Impl;

import com.wheatmall.search.config.ElasticsearchConfig;
import com.wheatmall.search.constant.EsConstant;
import com.wheatmall.search.remote.ProductRemoteService;
import com.wheatmall.search.service.MallSearchService;
import com.wheatmall.search.vo.SearchParam;
import com.wheatmall.search.vo.SearchResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private RestHighLevelClient esRestClient;

    @Resource
    private ProductRemoteService productRemoteService;

    @Override
    public SearchResult search(SearchParam param) {
        //  TODO: ES 检索逻辑待实现
        return new SearchResult();
    }
}

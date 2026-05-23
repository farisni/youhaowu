package com.wheatmall.search.service.Impl;

import com.wheatmall.common.vo.SkuEsModel;
import com.wheatmall.search.config.ElasticsearchConfig;
import com.wheatmall.search.constant.EsConstant;
import com.wheatmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient esRestClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //  TODO: ES 索引写入逻辑待实现
        return true;
    }
}

package com.youhaowu.search.service.Impl;

import com.youhaowu.common.vo.SkuEsModel;
import com.youhaowu.search.config.ElasticsearchConfig;
import com.youhaowu.search.constant.EsConstant;
import com.youhaowu.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private ElasticsearchClient esRestClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //  TODO: ES 索引写入逻辑待实现
        return true;
    }
}

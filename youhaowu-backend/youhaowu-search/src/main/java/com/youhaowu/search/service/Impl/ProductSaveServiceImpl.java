package com.youhaowu.search.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.youhaowu.common.vo.SkuEsModel;
import com.youhaowu.search.constant.EsConstant;
import com.youhaowu.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private ElasticsearchClient esRestClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) {
        if (skuEsModels == null || skuEsModels.isEmpty()) {
            log.warn("上架商品列表为空，跳过 ES 写入");
            return true;
        }
        try {
            //  构建 BulkRequest，每个 SKU 一条 index 操作
            List<BulkOperation> operations = skuEsModels.stream()
                    .map(model -> BulkOperation.of(b -> b
                            .index(idx -> idx
                                    .index(EsConstant.PRODUCT_INDEX)
                                    .id(String.valueOf(model.getSkuId()))
                                    .document(model))))
                    .collect(Collectors.toList());

            BulkResponse response = esRestClient.bulk(BulkRequest.of(b -> b.operations(operations)));

            if (response.errors()) {
                log.error("ES 批量写入有错误: {}", response.items().stream()
                        .filter(item -> item.error() != null)
                        .map(item -> item.error().reason())
                        .collect(Collectors.joining(", ")));
                return false;
            }
            log.info("ES 批量写入成功，共 {} 条", skuEsModels.size());
            return true;
        } catch (Exception e) {
            log.error("ES 批量写入异常: {}", e.getMessage(), e);
            return false;
        }
    }
}

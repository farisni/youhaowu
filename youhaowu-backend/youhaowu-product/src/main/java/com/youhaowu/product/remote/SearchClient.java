package com.youhaowu.product.remote;

import com.youhaowu.common.constant.SearchServiceUris;
import reactor.core.publisher.Mono;
import com.youhaowu.common.vo.SkuEsModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/**
 * Search 服务远程客户端（@HttpExchange 声明式）
 */
public interface SearchClient {

    @PostExchange(SearchServiceUris.SearchSave.PRODUCT_UP)
    Mono<Boolean> productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}

package com.youhaowu.common.remote;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.R;
import reactor.core.publisher.Mono;
import com.youhaowu.common.vo.SkuHasStockVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/**
 * Ware 库存服务远程客户端（@HttpExchange 声明式 + Nacos 服务发现）
 */
public interface WareClient {

    @PostExchange(WareServiceUris.WareSku.HAS_STOCK)
    Mono<R<List<SkuHasStockVO>>> hasStock(@RequestBody List<Long> skuIds);
}

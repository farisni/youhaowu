package com.youhaowu.product.remote;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.R;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Coupon 服务远程客户端（@HttpExchange 声明式）
 */
public interface CouponClient {

    @PostExchange(CouponServiceUris.SpuBounds.SAVE)
    Mono<R> saveSpuBounds(@RequestBody Object spuBoundTo);

    @PostExchange(CouponServiceUris.SkuFullReduction.SAVE)
    Mono<R> saveSkuReduction(@RequestBody Object skuReductionTo);
}

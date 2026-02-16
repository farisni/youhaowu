package com.wheatmall.order.service;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.order.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * 订单服务 - 使用WebClient调用Product模块
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient productWebClient;

    /**
     * 根据ID获取商品信息
     * @param productId 商品ID
     * @return 商品信息
     */
    public ProductDTO getProductById(Long productId) {
        log.info("使用WebClient调用Product模块获取商品信息, productId: {}", productId);
        
        return productWebClient.get()
                .uri(ServiceUris.Product.GET_BY_ID_INTERNAL, productId)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
    }

    /**
     * 获取所有商品列表
     * @return 商品列表
     */
    public List<ProductDTO> getProductList() {
        log.info("使用WebClient调用Product模块获取商品列表");
        
        return productWebClient.get()
                .uri(ServiceUris.Product.LIST)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductDTO>>() {})
                .block();
    }

    /**
     * 异步获取商品信息
     * @param productId 商品ID
     * @return 商品信息的Mono对象
     */
    public reactor.core.publisher.Mono<ProductDTO> getProductByIdAsync(Long productId) {
        log.info("使用WebClient异步调用Product模块获取商品信息, productId: {}", productId);
        
        return productWebClient.get()
                .uri(ServiceUris.Product.GET_BY_ID_INTERNAL, productId)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
}

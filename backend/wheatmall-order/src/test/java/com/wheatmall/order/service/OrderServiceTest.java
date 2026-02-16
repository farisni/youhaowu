package com.wheatmall.order.service;

import com.wheatmall.order.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * OrderService单元测试
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private WebClient productWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private OrderService orderService;

    private ProductDTO mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductDTO(
                1L,
                "测试商品",
                new BigDecimal("99.99"),
                100,
                "测试商品描述",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void testGetProductById() {
        // 模拟WebClient调用链
        when(productWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Long.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductDTO.class)).thenReturn(Mono.just(mockProduct));

        // 执行测试
        ProductDTO result = orderService.getProductById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("测试商品", result.getName());
        assertEquals(new BigDecimal("99.99"), result.getPrice());
    }

    @Test
    void testGetProductByIdAsync() {
        // 模拟WebClient调用链
        when(productWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Long.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ProductDTO.class)).thenReturn(Mono.just(mockProduct));

        // 执行测试
        Mono<ProductDTO> resultMono = orderService.getProductByIdAsync(1L);

        // 验证结果
        assertNotNull(resultMono);
        ProductDTO result = resultMono.block();
        assertEquals(1L, result.getId());
        assertEquals("测试商品", result.getName());
    }
}

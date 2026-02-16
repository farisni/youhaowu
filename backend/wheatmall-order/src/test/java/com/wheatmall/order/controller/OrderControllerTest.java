package com.wheatmall.order.controller;

import com.wheatmall.order.dto.ProductDTO;
import com.wheatmall.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * OrderController单元测试
 */
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ProductDTO mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductDTO(
                1L,
                "iPhone 15",
                new BigDecimal("5999.00"),
                50,
                "Apple iPhone 15",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void testGetProductById() {
        // 模拟service调用
        when(orderService.getProductById(1L)).thenReturn(mockProduct);

        // 执行测试
        ProductDTO result = orderController.getProductById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("iPhone 15", result.getName());
        assertEquals(new BigDecimal("5999.00"), result.getPrice());
    }

    @Test
    void testGetProductList() {
        // 准备测试数据
        ProductDTO product2 = new ProductDTO(
                2L,
                "MacBook Pro",
                new BigDecimal("12999.00"),
                30,
                "Apple MacBook Pro",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        List<ProductDTO> productList = Arrays.asList(mockProduct, product2);

        // 模拟service调用
        when(orderService.getProductList()).thenReturn(productList);

        // 执行测试
        List<ProductDTO> result = orderController.getProductList();

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("iPhone 15", result.get(0).getName());
        assertEquals("MacBook Pro", result.get(1).getName());
    }

    @Test
    void testCreateOrderSuccess() {
        // 模拟service调用
        when(orderService.getProductById(1L)).thenReturn(mockProduct);

        // 执行测试 - 库存充足
        String result = orderController.createOrder(1L, 10);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.contains("订单创建成功"));
        assertTrue(result.contains("iPhone 15"));
    }

    @Test
    void testCreateOrderProductNotFound() {
        // 模拟service调用 - 商品不存在
        when(orderService.getProductById(999L)).thenReturn(null);

        // 执行测试
        String result = orderController.createOrder(999L, 1);

        // 验证结果
        assertEquals("商品不存在", result);
    }

    @Test
    void testCreateOrderStockNotEnough() {
        // 模拟service调用
        when(orderService.getProductById(1L)).thenReturn(mockProduct);

        // 执行测试 - 库存不足（库存50，请求100）
        String result = orderController.createOrder(1L, 100);

        // 验证结果
        assertEquals("库存不足", result);
    }
}

package com.wheatmall.product.controller;

import com.wheatmall.common.utils.R;
import com.wheatmall.product.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProductController单元测试
 * 使用Mockito进行测试
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
    }

    @Test
    void testGetProductById() {
        // 执行测试
        R<Product> result = productController.getProductById(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("success", result.getMsg());
        assertNotNull(result.getData());

        Product product = result.getData();
        assertEquals(1L, product.getId());
        assertEquals("测试商品-1", product.getName());
    }

    @Test
    void testGetProductList() {
        // 执行测试
        R<List<Product>> result = productController.getProductList();

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertNotNull(result.getData());

        List<Product> products = result.getData();
        assertEquals(3, products.size());

        // 验证第一个商品
        Product firstProduct = products.get(0);
        assertEquals("iPhone 15", firstProduct.getName());
    }

    @Test
    void testGetProduct() {
        // 执行测试
        R<Product> result = productController.getProduct(2L);

        // 验证结果
        assertNotNull(result);
        assertEquals(0, result.getCode());

        Product product = result.getData();
        assertEquals(2L, product.getId());
        assertEquals("测试商品-2", product.getName());
    }
}

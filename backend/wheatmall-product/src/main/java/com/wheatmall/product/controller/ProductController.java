package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品控制器 - 提供商品相关的REST接口
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class ProductController {

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/{id}")
    public R getProductById(@PathVariable Long id) {
        // 模拟返回商品数据
        Product product = new Product(
                id,
                "测试商品-" + id,
                new BigDecimal("99.99"),
                100,
                "这是一个测试商品",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return R.ok().setData(product);
    }

    /**
     * 查询所有商品
     * @return 商品列表
     */
    @GetMapping("/list")
    public R getProductList() {
        List<Product> productList = new ArrayList<>();
        
        productList.add(new Product(
                1L,
                "iPhone 15",
                new BigDecimal("5999.00"),
                50,
                "Apple iPhone 15",
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
        
        productList.add(new Product(
                2L,
                "MacBook Pro",
                new BigDecimal("12999.00"),
                30,
                "Apple MacBook Pro",
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
        
        productList.add(new Product(
                3L,
                "AirPods Pro",
                new BigDecimal("1999.00"),
                100,
                "Apple AirPods Pro",
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
        
        return R.ok().setData(productList);
    }

    /**
     * 根据ID查询商品 - 专供内部服务调用
     * @param id 商品ID
     * @return 商品信息
     */
    @GetMapping("/get/{id}")
    public R getProduct(@PathVariable Long id) {
        return getProductById(id);
    }
}

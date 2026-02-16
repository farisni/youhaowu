package com.wheatmall.order.controller;

import com.wheatmall.order.dto.ProductDTO;
import com.wheatmall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 订单控制器 - 演示使用WebClient调用Product模块
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 根据商品ID获取商品信息（同步调用）
     * @param productId 商品ID
     * @return 商品信息
     */
    @GetMapping("/product/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return orderService.getProductById(productId);
    }

    /**
     * 获取所有商品列表（同步调用）
     * @return 商品列表
     */
    @GetMapping("/products")
    public List<ProductDTO> getProductList() {
        return orderService.getProductList();
    }

    /**
     * 根据商品ID获取商品信息（异步调用）
     * @param productId 商品ID
     * @return 商品信息的Mono对象
     */
    @GetMapping("/product/async/{productId}")
    public Mono<ProductDTO> getProductByIdAsync(@PathVariable Long productId) {
        return orderService.getProductByIdAsync(productId);
    }

    /**
     * 创建订单 - 演示调用Product模块获取商品信息
     * @param productId 商品ID
     * @param quantity 数量
     * @return 订单信息
     */
    @PostMapping("/create")
    public String createOrder(@RequestParam Long productId, @RequestParam Integer quantity) {
        ProductDTO product = orderService.getProductById(productId);
        
        if (product == null) {
            return "商品不存在";
        }
        
        if (product.getStock() < quantity) {
            return "库存不足";
        }
        
        // 模拟创建订单逻辑
        return String.format("订单创建成功！商品：%s，单价：%s，数量：%d，总价：%s",
                product.getName(),
                product.getPrice(),
                quantity,
                product.getPrice().multiply(new java.math.BigDecimal(quantity)));
    }
}

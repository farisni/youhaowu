package com.youhaowu.cart.controller;

import com.youhaowu.common.utils.R;
import com.youhaowu.common.vo.cart.CartItemVO;
import com.youhaowu.common.vo.cart.CartVO;
import com.youhaowu.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     */
    @GetMapping("/add")
    public R<CartItemVO> add(@RequestParam Long skuId, @RequestParam Integer num)
            throws ExecutionException, InterruptedException {
        return R.ok(cartService.addToCart(skuId, num));
    }

    /**
     * 获取购物车
     */
    @GetMapping("/list")
    public R<CartVO> list() throws ExecutionException, InterruptedException {
        return R.ok(cartService.getCart());
    }

    /**
     * 获取单个购物项
     */
    @GetMapping("/item/{skuId}")
    public R<CartItemVO> item(@PathVariable Long skuId) {
        return R.ok(cartService.getCartItem(skuId));
    }

    /**
     * 切换选中状态
     */
    @GetMapping("/check")
    public R<Integer> check(@RequestParam Long skuId, @RequestParam Integer checked) {
        return R.ok(cartService.checkItem(skuId, checked));
    }

    /**
     * 修改商品数量
     */
    @GetMapping("/count")
    public R<Integer> count(@RequestParam Long skuId, @RequestParam Integer num) {
        return R.ok(cartService.changeItemCount(skuId, num));
    }

    /**
     * 删除购物项
     */
    @GetMapping("/delete/{skuId}")
    public R<Integer> delete(@PathVariable Long skuId) {
        return R.ok(cartService.deleteItem(skuId));
    }

    /**
     * 获取当前用户选中的商品（结算用，实时价格）
     */
    @GetMapping("/currentUserCartItems")
    public R<List<CartItemVO>> currentUserCartItems() {
        return R.ok(cartService.getUserCartItems());
    }
}

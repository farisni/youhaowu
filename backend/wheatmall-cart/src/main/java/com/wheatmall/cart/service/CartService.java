package com.wheatmall.cart.service;

import com.wheatmall.common.vo.cart.CartItemVO;
import com.wheatmall.common.vo.cart.CartVO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {

    CartItemVO addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    CartItemVO getCartItem(Long skuId);

    CartVO getCart() throws ExecutionException, InterruptedException;

    Integer clearCart(String cartKey);

    Integer checkItem(Long skuId, Integer check);

    Integer changeItemCount(Long skuId, Integer num);

    Integer deleteItem(Long skuId);

    List<CartItemVO> getUserCartItems();
}

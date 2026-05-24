package com.wheatmall.cart.exception;

public class CartExceptionHandler extends RuntimeException {
    public CartExceptionHandler() {
        super("购物车无此商品");
    }
}

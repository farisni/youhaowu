package com.youhaowu.cart.handler;

import com.youhaowu.cart.exception.CartExceptionHandler;
import com.youhaowu.common.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionAdvice {

    @ExceptionHandler(CartExceptionHandler.class)
    public R handleCartException(CartExceptionHandler e) {
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntime(RuntimeException e) {
        return R.fail(e.getMessage());
    }
}

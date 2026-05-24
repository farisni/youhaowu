package com.wheatmall.cart.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wheatmall.common.constant.CartConstant;
import com.wheatmall.common.to.cart.UserInfoTO;
import com.wheatmall.common.utils.R;
import com.wheatmall.common.vo.cart.CartItemVO;
import com.wheatmall.common.vo.cart.CartVO;
import com.wheatmall.cart.exception.CartExceptionHandler;
import com.wheatmall.cart.interceptor.CartInterceptor;
import com.wheatmall.cart.remote.ProductRemoteService;
import com.wheatmall.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductRemoteService productRemoteService;
    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public CartItemVO addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());
        if (StringUtils.isEmpty(json)) {
            //  新增商品到购物车
            CartItemVO cartItem = new CartItemVO();
            //  TODO: ProductRemoteService WebClient 实现后，从 R 提取 SkuInfoVO
            CompletableFuture<Void> infoFuture = CompletableFuture.runAsync(() -> {
                productRemoteService.getInfo(skuId);
            }, executor);

            CompletableFuture<Void> attrFuture = CompletableFuture.runAsync(() -> {
                List<String> attrs = productRemoteService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttrValues(attrs);
            }, executor);

            CompletableFuture.allOf(infoFuture, attrFuture).get();
            cartItem.setSkuId(skuId);
            cartItem.setCount(num);
            cartItem.setCheck(true);
            ops.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        } else {
            //  已存在，累加数量
            CartItemVO cartItem = JSON.parseObject(json, CartItemVO.class);
            cartItem.setCount(cartItem.getCount() + num);
            ops.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        }
    }

    @Override
    public CartItemVO getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());
        return JSON.parseObject(json, CartItemVO.class);
    }

    @Override
    public CartVO getCart() throws ExecutionException, InterruptedException {
        CartVO cart = new CartVO();
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();
        List<CartItemVO> touristItems = getCartItems(CartConstant.CART_PREFIX + userInfo.getUserKey());
        if (userInfo.getUserId() != null) {
            if (!CollectionUtils.isEmpty(touristItems)) {
                for (CartItemVO item : touristItems) {
                    addToCart(item.getSkuId(), item.getCount());
                }
                clearCart(CartConstant.CART_PREFIX + userInfo.getUserKey());
            }
            cart.setItems(getCartItems(CartConstant.CART_PREFIX + userInfo.getUserId()));
        } else {
            cart.setItems(touristItems);
        }
        return cart;
    }

    @Override
    public Integer clearCart(String cartKey) {
        return redisTemplate.delete(cartKey) ? 1 : 0;
    }

    @Override
    public Integer checkItem(Long skuId, Integer check) {
        CartItemVO item = getCartItem(skuId);
        if (item == null) return 0;
        item.setCheck(check == 1);
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        ops.put(skuId.toString(), JSON.toJSONString(item));
        return 1;
    }

    @Override
    public Integer changeItemCount(Long skuId, Integer num) {
        CartItemVO item = getCartItem(skuId);
        if (item == null) return 0;
        item.setCount(num);
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        ops.put(skuId.toString(), JSON.toJSONString(item));
        return 1;
    }

    @Override
    public Integer deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        return ops.delete(skuId.toString()) > 0 ? 1 : 0;
    }

    @Override
    public List<CartItemVO> getUserCartItems() {
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();
        if (userInfo.getUserId() == null) return null;
        List<CartItemVO> items = getCartItems(CartConstant.CART_PREFIX + userInfo.getUserId());
        if (CollectionUtils.isEmpty(items)) throw new CartExceptionHandler();
        Map<Long, CartItemVO> itemMap = items.stream().filter(CartItemVO::getCheck)
                .collect(Collectors.toMap(CartItemVO::getSkuId, v -> v));
        Map<Long, BigDecimal> priceMap = productRemoteService.getPrice(itemMap.keySet());
        return itemMap.entrySet().stream().map(entry -> {
            CartItemVO item = entry.getValue();
            BigDecimal newPrice = priceMap.get(entry.getKey());
            if (newPrice != null) item.setPrice(newPrice);
            return item;
        }).collect(Collectors.toList());
    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();
        String cartKey = userInfo.getUserId() != null
                ? CartConstant.CART_PREFIX + userInfo.getUserId()
                : CartConstant.CART_PREFIX + userInfo.getUserKey();
        return redisTemplate.boundHashOps(cartKey);
    }

    private List<CartItemVO> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(cartKey);
        List<Object> values = ops.values();
        if (CollectionUtils.isEmpty(values)) return null;
        return values.stream()
                .map(v -> JSONObject.parseObject((String) v, CartItemVO.class))
                .collect(Collectors.toList());
    }
}

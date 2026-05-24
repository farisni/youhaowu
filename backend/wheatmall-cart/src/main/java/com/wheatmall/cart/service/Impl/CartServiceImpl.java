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

    //  ==================== 对外接口 ====================

    /**
     * 添加商品到购物车
     * 1. 根据用户标识获取 Redis Hash 操作对象
     * 2. 商品不存在：异步查 Product 服务获取 SKU 信息、销售属性，构建 CartItemVO 写入 Redis
     * 3. 商品已存在：累加数量后更新 Redis
     */
    @Override
    public CartItemVO addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        //  获取当前用户购物车 Redis 操作对象
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());
        if (StringUtils.isEmpty(json)) {
            //  商品不存在：新建购物项
            CartItemVO cartItem = new CartItemVO();

            //  异步查询 SKU 基本信息（TODO: ProductRemoteService WebClient 实现后解析 R.data）
            CompletableFuture<Void> infoFuture = CompletableFuture.runAsync(() -> {
                productRemoteService.getInfo(skuId);
            }, executor);

            //  异步查询 SKU 销售属性
            CompletableFuture<Void> attrFuture = CompletableFuture.runAsync(() -> {
                List<String> attrs = productRemoteService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttrValues(attrs);
            }, executor);

            //  等待异步任务完成
            CompletableFuture.allOf(infoFuture, attrFuture).get();
            cartItem.setSkuId(skuId);
            cartItem.setCount(num);
            cartItem.setCheck(true);

            //  写入 Redis Hash
            ops.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        } else {
            //  商品已存在：累加数量
            CartItemVO cartItem = JSON.parseObject(json, CartItemVO.class);
            cartItem.setCount(cartItem.getCount() + num);
            ops.put(skuId.toString(), JSON.toJSONString(cartItem));
            return cartItem;
        }
    }

    /**
     * 获取单个购物项
     * 从 Redis Hash 中按 skuId 读取购物车商品，反序列化为 CartItemVO
     */
    @Override
    public CartItemVO getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());
        return JSON.parseObject(json, CartItemVO.class);
    }

    /**
     * 获取购物车列表
     * 1. 从 ThreadLocal 获取用户信息（拦截器注入）
     * 2. 登录用户：查询游客购物车并合并到用户购物车，清除游客购物车
     * 3. 未登录用户：直接返回游客购物车
     */
    @Override
    public CartVO getCart() throws ExecutionException, InterruptedException {
        CartVO cart = new CartVO();
        //  从拦截器获取用户信息
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();

        //  查询游客购物车
        List<CartItemVO> touristItems = getCartItems(CartConstant.CART_PREFIX + userInfo.getUserKey());
        if (userInfo.getUserId() != null) {
            //  登录态：合并游客购物车到用户购物车
            if (!CollectionUtils.isEmpty(touristItems)) {
                for (CartItemVO item : touristItems) {
                    addToCart(item.getSkuId(), item.getCount());
                }
                //  清除游客购物车
                clearCart(CartConstant.CART_PREFIX + userInfo.getUserKey());
            }
            //  返回合并后的用户购物车
            cart.setItems(getCartItems(CartConstant.CART_PREFIX + userInfo.getUserId()));
        } else {
            //  未登录：返回游客购物车
            cart.setItems(touristItems);
        }
        return cart;
    }

    /**
     * 清空购物车
     * 删除指定 cartKey 对应的 Redis 整个 Hash
     */
    @Override
    public Integer clearCart(String cartKey) {
        return redisTemplate.delete(cartKey) ? 1 : 0;
    }

    /**
     * 切换购物项选中状态
     * 1. 从 Redis 读取购物项
     * 2. 更新 check 字段后写回
     * @return 成功返回 1，购物项不存在返回 0
     */
    @Override
    public Integer checkItem(Long skuId, Integer check) {
        //  获取购物项
        CartItemVO item = getCartItem(skuId);
        if (item == null) return 0;
        //  更新选中状态
        item.setCheck(check == 1);
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        ops.put(skuId.toString(), JSON.toJSONString(item));
        return 1;
    }

    /**
     * 修改购物项数量
     * 1. 从 Redis 读取购物项
     * 2. 更新数量后写回
     * @return 成功返回 1，购物项不存在返回 0
     */
    @Override
    public Integer changeItemCount(Long skuId, Integer num) {
        //  获取购物项并更新数量
        CartItemVO item = getCartItem(skuId);
        if (item == null) return 0;
        item.setCount(num);
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        ops.put(skuId.toString(), JSON.toJSONString(item));
        return 1;
    }

    /**
     * 删除购物项
     * 从 Redis Hash 中移除指定 skuId 的购物项
     * @return 成功返回 1，未删除返回 0
     */
    @Override
    public Integer deleteItem(Long skuId) {
        //  从 Redis Hash 删除购物项
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        return ops.delete(skuId.toString()) > 0 ? 1 : 0;
    }

    /**
     * 获取当前用户选中的购物项（结算用）
     * 1. 从 Redis 查询用户购物车，筛选选中项
     * 2. 调用 Product 服务获取最新价格
     * 3. 替换 Redis 中的缓存价格为实时价格
     */
    @Override
    public List<CartItemVO> getUserCartItems() {
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();
        if (userInfo.getUserId() == null) return null;

        //  查询 Redis 用户购物车
        List<CartItemVO> items = getCartItems(CartConstant.CART_PREFIX + userInfo.getUserId());
        if (CollectionUtils.isEmpty(items)) throw new CartExceptionHandler();

        //  筛选选中的 SKU
        Map<Long, CartItemVO> itemMap = items.stream().filter(CartItemVO::getCheck)
                .collect(Collectors.toMap(CartItemVO::getSkuId, v -> v));

        //  调用远程获取最新价格
        Map<Long, BigDecimal> priceMap = productRemoteService.getPrice(itemMap.keySet());

        //  替换为实时价格
        return itemMap.entrySet().stream().map(entry -> {
            CartItemVO item = entry.getValue();
            BigDecimal newPrice = priceMap.get(entry.getKey());
            if (newPrice != null) item.setPrice(newPrice);
            return item;
        }).collect(Collectors.toList());
    }

    //  ==================== 内部工具方法 ====================

    /**
     * 根据用户标识获取 Redis Hash 操作对象
     * 登录用户 → wheatmall:cart:{userId}
     * 游客     → wheatmall:cart:{userKey}
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTO userInfo = CartInterceptor.threadLocal.get();
        String cartKey = userInfo.getUserId() != null
                ? CartConstant.CART_PREFIX + userInfo.getUserId()
                : CartConstant.CART_PREFIX + userInfo.getUserKey();
        return redisTemplate.boundHashOps(cartKey);
    }

    /**
     * 从 Redis 获取指定 key 的购物车商品列表
     */
    private List<CartItemVO> getCartItems(String cartKey) {
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(cartKey);
        List<Object> values = ops.values();
        if (CollectionUtils.isEmpty(values)) return null;
        return values.stream()
                .map(v -> JSONObject.parseObject((String) v, CartItemVO.class))
                .collect(Collectors.toList());
    }
}

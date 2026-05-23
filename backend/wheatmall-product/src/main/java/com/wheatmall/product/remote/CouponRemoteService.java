package com.wheatmall.product.remote;

import com.wheatmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Coupon 服务调用占位
 * TODO: 待 Coupon 模块就绪后，改为 WebClient 调用
 */
@Slf4j
@Service
public class CouponRemoteService {

    public R saveSpuBounds(Object spuBoundTo) {
        log.info("CouponRemoteService.saveSpuBounds - 占位返回");
        // TODO: 改为 WebClient 调用 wheatmall-coupon 服务
        return R.ok();
    }

    public R saveSkuReduction(Object skuReductionTo) {
        log.info("CouponRemoteService.saveSkuReduction - 占位返回");
        // TODO: 改为 WebClient 调用 wheatmall-coupon 服务
        return R.ok();
    }
}

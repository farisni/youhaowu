package com.youhaowu.seckill.remote;

import com.youhaowu.common.utils.R;
import org.springframework.stereotype.Component;

/**
 * Coupon 服务远程调用（TODO: WebClient 实现）
 */
@Component
public class CouponRemoteService {

    /**
     * 查询最近三天需要参加秒杀的场次 + 商品
     */
    public R getLates3DaySession() {
        //  TODO: WebClient 实现
        return R.fail("CouponRemoteService.getLates3DaySession 未实现");
    }
}

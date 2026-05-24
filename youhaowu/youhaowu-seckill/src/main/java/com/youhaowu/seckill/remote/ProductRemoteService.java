package com.youhaowu.seckill.remote;

import com.youhaowu.common.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Product 服务远程调用（TODO: WebClient 实现）
 */
@Component
public class ProductRemoteService {

    /**
     * 查询单个 SKU 信息
     */
    public R getSkuInfo(Long skuId) {
        //  TODO: WebClient 实现
        return R.fail("ProductRemoteService.getSkuInfo 未实现");
    }

    /**
     * 批量查询 SKU 信息
     */
    public R getSkuInfos(List<Long> skuIds) {
        //  TODO: WebClient 实现
        return R.fail("ProductRemoteService.getSkuInfos 未实现");
    }
}

package com.wheatmall.product.remote;

import com.wheatmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ware 库存服务调用占位
 * TODO: 待 Ware 模块就绪后，改为 WebClient 调用
 */
@Slf4j
@Service
public class WareRemoteService {

    public R getSkuHasStock(List<Long> skuIds) {
        log.info("WareRemoteService.getSkuHasStock - 占位返回（全部有库存）");
        // TODO: 改为 WebClient 调用 wheatmall-ware 服务
        return R.ok();
    }
}

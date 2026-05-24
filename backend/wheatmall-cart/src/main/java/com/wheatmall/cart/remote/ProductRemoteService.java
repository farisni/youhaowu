package com.wheatmall.cart.remote;

import com.wheatmall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Product 服务远程调用占位
 * TODO: 改为 WebClient 调用 wheatmall-product
 */
@Slf4j
@Service
public class ProductRemoteService {

    public R getInfo(Long skuId) {
        log.info("ProductRemoteService.getInfo({}) - 占位", skuId);
        return R.ok();
    }

    public List<String> getSkuSaleAttrValues(Long skuId) {
        log.info("ProductRemoteService.getSkuSaleAttrValues({}) - 占位", skuId);
        return Collections.emptyList();
    }

    public Map<Long, BigDecimal> getPrice(Collection<Long> skuIds) {
        log.info("ProductRemoteService.getPrice({}) - 占位", skuIds.size());
        return Collections.emptyMap();
    }
}

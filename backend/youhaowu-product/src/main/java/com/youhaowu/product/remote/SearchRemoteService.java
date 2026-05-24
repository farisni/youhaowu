package com.youhaowu.product.remote;

import com.youhaowu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Search 搜索服务调用占位
 * TODO: 待 Search 模块就绪后，改为 WebClient 调用
 */
@Slf4j
@Service
public class SearchRemoteService {

    public R productStatusUp(Object skuEsModels) {
        log.info("SearchRemoteService.productStatusUp - 占位返回");
        // TODO: 改为 WebClient 调用 youhaowu-search 服务
        return R.ok();
    }
}

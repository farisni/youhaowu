package com.youhaowu.search.service;

import com.youhaowu.common.vo.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;

}

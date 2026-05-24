package com.youhaowu.search.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youhaowu.common.enums.BizCodeEnum;
import com.youhaowu.search.service.ProductSaveService;
import com.youhaowu.common.vo.SkuEsModel;
import com.youhaowu.common.utils.R;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping(value = "/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;


    /**
     * 上架商品
     * @param skuEsModels
     * @return
     */
    @PostMapping(value = "/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {

        boolean status=false;
        try {
            status = productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            //log.error("商品上架错误{}",e);

            return R.fail(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_UP_EXCEPTION.getMessage());
        }

        if(status){
            return R.fail(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_UP_EXCEPTION.getMessage());
        }else {
            return R.ok();
        }

    }
}

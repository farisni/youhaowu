package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.constant.ProductConstant;
import com.youhaowu.product.entity.*;
import com.youhaowu.product.mapper.*;
import com.youhaowu.product.remote.CouponRemoteService;
import com.youhaowu.product.remote.SearchRemoteService;
import com.youhaowu.product.remote.WareRemoteService;
import com.youhaowu.product.service.*;
import com.youhaowu.product.vo.*;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SpuInfoServiceImpl implements SpuInfoService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesMapper skuImagesMapper;
    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Autowired
    private CouponRemoteService couponRemoteService;
    @Autowired
    private WareRemoteService wareRemoteService;
    @Autowired
    private SearchRemoteService searchRemoteService;

    @Override
    public PageData<SpuInfoVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuInfoMapper, new LambdaQueryWrapper<>(), query, e -> {
            SpuInfoVO v = new SpuInfoVO();
            BeanUtil.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public SpuInfoVO getVOById(Long id) {
        SpuInfoEntity e = spuInfoMapper.selectById(id);
        SpuInfoVO v = new SpuInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }

    @Override
    public Integer saveBaseSpuInfo(SpuInfoVO vo) {
        SpuInfoEntity entity = new SpuInfoEntity();
        BeanUtil.copyProperties(vo, entity);
        return spuInfoMapper.insert(entity);
    }

    /**
     * 保存SPU完整信息（含描述、图片、属性、SKU、积分）
     * 流程：SPU基本信息 → 描述 → 图片 → 基本属性 → 积分 → SKU列表（基本信息+图片+销售属性+会员价）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveSupInfo(SpuSaveVO vo) {
        //  1. 保存SPU基本信息
        SpuInfoEntity spu = new SpuInfoEntity();
        BeanUtil.copyProperties(vo, spu);
        spu.setCreateTime(new Date());
        spu.setUpdateTime(new Date());
        int affected = spuInfoMapper.insert(spu);

        //  2. 保存SPU描述
        List<String> decript = vo.getDecript();
        if (decript != null) {
            SpuInfoDescVO descVO = new SpuInfoDescVO();
            descVO.setSpuId(spu.getId());
            descVO.setDecript(String.join(",", decript));
            spuInfoDescService.saveSpuInfoDesc(descVO);
        }

        //  3. 保存SPU图片集
        spuImagesService.saveImages(spu.getId(), vo.getImages());

        //  4. 保存SPU基本属性（规格参数）
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        if (baseAttrs != null) {
            List<ProductAttrValueVO> attrs = baseAttrs.stream().map(a -> {
                ProductAttrValueVO v = new ProductAttrValueVO();
                v.setAttrId(a.getAttrId()); v.setAttrValue(a.getAttrValues());
                v.setQuickShow(a.getShowDesc()); v.setSpuId(spu.getId());
                return v;
            }).collect(Collectors.toList());
            productAttrValueService.saveProductAttr(attrs);
        }

        //  5. 保存积分信息（远程调用）
        if (vo.getBounds() != null) couponRemoteService.saveSpuBounds(vo.getBounds());

        //  6. 遍历保存SKU信息
        List<Skus> skus = vo.getSkus();
        if (skus != null) {
            skus.forEach(item -> {
                //  6a. 构建SKU基本信息并保存
                SkuInfoVO sku = new SkuInfoVO();
                BeanUtil.copyProperties(item, sku);
                sku.setSpuId(spu.getId()); sku.setCatalogId(vo.getCatalogId());
                sku.setBrandId(vo.getBrandId()); sku.setSaleCount(0L);
                String defaultImg = "";
                if (item.getImages() != null) {
                    for (Images img : item.getImages()) {
                        if (img.getDefaultImg() == 1) { defaultImg = img.getImgUrl(); break; }
                    }
                }
                sku.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(sku);
                Long skuId = sku.getSkuId();

                //  6b. 保存SKU图片
                if (item.getImages() != null) {
                    for (Images i : item.getImages()) {
                        if (StrUtil.isNotBlank(i.getImgUrl())) {
                            SkuImagesEntity e = new SkuImagesEntity();
                            e.setSkuId(skuId); e.setImgUrl(i.getImgUrl());
                            e.setDefaultImg(i.getDefaultImg());
                            skuImagesMapper.insert(e);
                        }
                    }
                }

                //  6c. 保存SKU销售属性
                List<Attr> saleAttrs = item.getAttr();
                if (saleAttrs != null) {
                    saleAttrs.forEach(a -> {
                        SkuSaleAttrValueEntity e = new SkuSaleAttrValueEntity();
                        BeanUtil.copyProperties(a, e); e.setSkuId(skuId);
                        skuSaleAttrValueMapper.insert(e);
                    });
                }

                //  6d. 保存SKU会员价格（远程调用）
                if (item.getMemberPrice() != null) couponRemoteService.saveSkuReduction(item);
            });
        }
        return affected;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer up(Long spuId) {
        List<SkuInfoVO> skus = skuInfoService.getSkusBySpuId(spuId);
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);
        List<Long> skuIds = skus.stream().map(SkuInfoVO::getSkuId).collect(Collectors.toList());
        try { wareRemoteService.getSkuHasStock(skuIds); } catch (Exception ex) { log.error("库存查询异常: {}", ex.getMessage()); }
        try { searchRemoteService.productStatusUp(skus); } catch (Exception ex) { log.error("搜索上架异常: {}", ex.getMessage()); }
        return spuInfoMapper.updaSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
    }
}

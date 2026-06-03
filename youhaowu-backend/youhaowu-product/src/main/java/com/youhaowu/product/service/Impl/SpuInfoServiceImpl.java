package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.constant.ProductConstant;
import com.youhaowu.common.constant.KafkaTopicConstants;
import com.youhaowu.product.entity.*;
import com.youhaowu.product.mapper.*;
import com.youhaowu.product.entity.BrandEntity;
import com.youhaowu.product.entity.CategoryEntity;
import com.youhaowu.product.remote.CouponClient;
import com.youhaowu.product.remote.SearchClient;
import com.youhaowu.common.remote.WareClient;
import com.youhaowu.product.service.*;
import com.youhaowu.product.vo.*;
import com.youhaowu.common.vo.SkuEsModel;
import com.youhaowu.common.vo.SkuHasStockVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private CouponClient couponClient;

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private WareClient wareClient;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

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
        if (vo.getBounds() != null) couponClient.saveSpuBounds(vo.getBounds()).subscribe();

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
                if (item.getMemberPrice() != null) couponClient.saveSkuReduction(item).subscribe();
            });
        }
        return affected;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer up(Long spuId) {
        //  1. 查询 SPU 下所有 SKU
        List<SkuInfoVO> skuVOs = skuInfoService.getSkusBySpuId(spuId);

        //  2. 查询 SPU 关联的基本属性，筛选可检索属性
        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<SkuEsModel.Attrs> attrEsModels;
        if (attrIds.isEmpty()) {
            attrEsModels = List.of();
        } else {
            List<Long> searchAttrIds = attrService.selectSearchAttrIds(attrIds);
            attrEsModels = baseAttrs.stream()
                    .filter(a -> searchAttrIds.contains(a.getAttrId()))
                    .map(a -> {
                        SkuEsModel.Attrs m = new SkuEsModel.Attrs();
                        m.setAttrId(a.getAttrId());
                        m.setAttrName(a.getAttrName());
                        m.setAttrValue(a.getAttrValue());
                        return m;
                    }).collect(Collectors.toList());
        }

        //  3. 查询库存
        List<Long> skuIds = skuVOs.stream().map(SkuInfoVO::getSkuId).collect(Collectors.toList());
        Map<Long, Boolean> stockMap = null;
        try {
            stockMap = wareClient.hasStock(skuIds).block().stream()
                    .collect(Collectors.toMap(SkuHasStockVO::getSkuId, SkuHasStockVO::getHasStock));
        } catch (Exception ex) {
            log.error("库存查询异常: {}", ex.getMessage());
        }
        Map<Long, Boolean> finalStockMap = stockMap;

        //  4. 组装 SkuEsModel 列表
        List<SkuEsModel> esModels = skuVOs.stream().map(sku -> {
            SkuEsModel m = new SkuEsModel();
            m.setSkuId(sku.getSkuId());
            m.setSpuId(sku.getSpuId());
            m.setSkuTitle(sku.getSkuTitle());
            m.setSkuPrice(sku.getPrice());
            m.setSkuImg(sku.getSkuDefaultImg());
            m.setSaleCount(sku.getSaleCount());
            m.setHasStock(finalStockMap == null || finalStockMap.getOrDefault(sku.getSkuId(), true));
            m.setHotScore(0L);
            m.setBrandId(sku.getBrandId());
            m.setCatalogId(sku.getCatalogId());
            //  品牌名
            try {
                BrandEntity brand = brandMapper.selectById(sku.getBrandId());
                m.setBrandName(brand.getName());
                m.setBrandImg(brand.getLogo());
            } catch (Exception e) {
                log.error("品牌查询异常: {}", e.getMessage());
            }
            //  分类名
            try {
                CategoryEntity category = categoryMapper.selectById(sku.getCatalogId());
                m.setCatalogName(category.getName());
            } catch (Exception e) {
                log.error("分类查询异常: {}", e.getMessage());
            }
            m.setAttrs(attrEsModels);
            return m;
        }).collect(Collectors.toList());

        //  5. 更新 SPU 状态为上架
        int rows = spuInfoMapper.updaSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());

        //  6. 发送 Kafka 消息（异步通知搜索服务构建索引）
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(esModels);
            kafkaTemplate.send(KafkaTopicConstants.TOPIC_PRODUCT_UP, json);
            log.info("SPU[{}] 上架消息已发送至 Kafka topic: {}", spuId, KafkaTopicConstants.TOPIC_PRODUCT_UP);
        } catch (Exception ex) {
            log.error("Kafka 发送异常: {}", ex.getMessage());
        }
        return rows;
    }
}

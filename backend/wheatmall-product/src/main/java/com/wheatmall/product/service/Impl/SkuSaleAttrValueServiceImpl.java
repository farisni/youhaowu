package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuSaleAttrValueVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuSaleAttrValueEntity;
import com.wheatmall.product.mapper.SkuSaleAttrValueMapper;
import com.wheatmall.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import com.wheatmall.product.vo.SkuSaleAttrValueVO;
import org.apache.ibatis.executor.BatchResult;
/**
 * SkuSaleAttrValue Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SkuSaleAttrValueServiceImpl implements SkuSaleAttrValueService {

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Override
    public PageData<SkuSaleAttrValueVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(skuSaleAttrValueMapper, new LambdaQueryWrapper<>(), query, e -> {
        SkuSaleAttrValueVO vo = new SkuSaleAttrValueVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SkuSaleAttrValueEntity getById(Long id) {
        return skuSaleAttrValueMapper.selectById(id);
    }

    @Override
    public Integer save(SkuSaleAttrValueVO vo) {
        SkuSaleAttrValueEntity e = new SkuSaleAttrValueEntity();
        BeanUtil.copyProperties(vo, e);
        return skuSaleAttrValueMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SkuSaleAttrValueVO> list) {
        List<SkuSaleAttrValueEntity> entities = list.stream().map(vo -> {
            SkuSaleAttrValueEntity e = new SkuSaleAttrValueEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return skuSaleAttrValueMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(SkuSaleAttrValueVO vo) {
        SkuSaleAttrValueEntity e = new SkuSaleAttrValueEntity();
        BeanUtil.copyProperties(vo, e);
        return skuSaleAttrValueMapper.updateById(e);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        skuSaleAttrValueMapper.deleteBatchIds(ids);
    }
}

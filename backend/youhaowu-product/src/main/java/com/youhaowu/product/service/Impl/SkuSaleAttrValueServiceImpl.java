package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SkuSaleAttrValueVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.SkuSaleAttrValueEntity;
import com.youhaowu.product.mapper.SkuSaleAttrValueMapper;
import com.youhaowu.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
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
    public SkuSaleAttrValueVO getById(Long id) {
        SkuSaleAttrValueEntity e = skuSaleAttrValueMapper.selectById(id);
        if (e == null) return null;
        SkuSaleAttrValueVO vo = new SkuSaleAttrValueVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
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
    public Integer removeByIds(List<Long> ids) {
        return skuSaleAttrValueMapper.deleteBatchIds(ids);
    }
}

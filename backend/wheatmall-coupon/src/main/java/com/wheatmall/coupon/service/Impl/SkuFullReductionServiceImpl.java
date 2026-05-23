package com.wheatmall.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.entity.SkuFullReductionEntity;
import com.wheatmall.coupon.mapper.SkuFullReductionMapper;
import com.wheatmall.coupon.dto.SkuFullReductionQueryDTO;
import com.wheatmall.coupon.service.SkuFullReductionService;
import com.wheatmall.coupon.vo.SkuFullReductionVO;
import com.wheatmall.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class SkuFullReductionServiceImpl implements SkuFullReductionService {

    @Autowired
    private SkuFullReductionMapper skuFullReductionMapper;

    @Override
    public PageData<SkuFullReductionVO> page(SkuFullReductionQueryDTO query) {
        LambdaQueryWrapper<SkuFullReductionEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(skuFullReductionMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SkuFullReductionVO getById(Long id) {
        SkuFullReductionEntity e = skuFullReductionMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SkuFullReductionVO vo) {
        SkuFullReductionEntity e = new SkuFullReductionEntity();
        BeanUtil.copyProperties(vo, e);
        return skuFullReductionMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SkuFullReductionVO> list) {
        List<SkuFullReductionEntity> entities = list.stream().map(vo -> {
            SkuFullReductionEntity e = new SkuFullReductionEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return skuFullReductionMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SkuFullReductionVO vo) {
        SkuFullReductionEntity e = new SkuFullReductionEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return skuFullReductionMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return skuFullReductionMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return skuFullReductionMapper.deleteBatchIds(ids);
    }

    private SkuFullReductionVO entityToVO(SkuFullReductionEntity e) {
        SkuFullReductionVO v = new SkuFullReductionVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

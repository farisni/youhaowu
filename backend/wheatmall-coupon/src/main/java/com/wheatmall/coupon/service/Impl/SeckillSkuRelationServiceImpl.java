package com.wheatmall.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.entity.SeckillSkuRelationEntity;
import com.wheatmall.coupon.mapper.SeckillSkuRelationMapper;
import com.wheatmall.coupon.dto.SeckillSkuRelationQueryDTO;
import com.wheatmall.coupon.service.SeckillSkuRelationService;
import com.wheatmall.coupon.vo.SeckillSkuRelationVO;
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
public class SeckillSkuRelationServiceImpl implements SeckillSkuRelationService {

    @Autowired
    private SeckillSkuRelationMapper seckillSkuRelationMapper;

    @Override
    public PageData<SeckillSkuRelationVO> page(SeckillSkuRelationQueryDTO query) {
        LambdaQueryWrapper<SeckillSkuRelationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(seckillSkuRelationMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SeckillSkuRelationVO getById(Long id) {
        SeckillSkuRelationEntity e = seckillSkuRelationMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SeckillSkuRelationVO vo) {
        SeckillSkuRelationEntity e = new SeckillSkuRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return seckillSkuRelationMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SeckillSkuRelationVO> list) {
        List<SeckillSkuRelationEntity> entities = list.stream().map(vo -> {
            SeckillSkuRelationEntity e = new SeckillSkuRelationEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return seckillSkuRelationMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SeckillSkuRelationVO vo) {
        SeckillSkuRelationEntity e = new SeckillSkuRelationEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return seckillSkuRelationMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return seckillSkuRelationMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return seckillSkuRelationMapper.deleteBatchIds(ids);
    }

    private SeckillSkuRelationVO entityToVO(SeckillSkuRelationEntity e) {
        SeckillSkuRelationVO v = new SeckillSkuRelationVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

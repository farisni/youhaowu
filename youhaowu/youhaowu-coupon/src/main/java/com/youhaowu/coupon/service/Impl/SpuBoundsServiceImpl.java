package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.SpuBoundsEntity;
import com.youhaowu.coupon.mapper.SpuBoundsMapper;
import com.youhaowu.coupon.dto.SpuBoundsQueryDTO;
import com.youhaowu.coupon.service.SpuBoundsService;
import com.youhaowu.coupon.vo.SpuBoundsVO;
import com.youhaowu.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class SpuBoundsServiceImpl implements SpuBoundsService {

    @Autowired
    private SpuBoundsMapper spuBoundsMapper;

    @Override
    public PageData<SpuBoundsVO> page(SpuBoundsQueryDTO query) {
        LambdaQueryWrapper<SpuBoundsEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(spuBoundsMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SpuBoundsVO getById(Long id) {
        SpuBoundsEntity e = spuBoundsMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SpuBoundsVO vo) {
        SpuBoundsEntity e = new SpuBoundsEntity();
        BeanUtil.copyProperties(vo, e);
        return spuBoundsMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SpuBoundsVO> list) {
        List<SpuBoundsEntity> entities = list.stream().map(vo -> {
            SpuBoundsEntity e = new SpuBoundsEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return spuBoundsMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SpuBoundsVO vo) {
        SpuBoundsEntity e = new SpuBoundsEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return spuBoundsMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return spuBoundsMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return spuBoundsMapper.deleteByIds(ids);
    }

    private SpuBoundsVO entityToVO(SpuBoundsEntity e) {
        SpuBoundsVO v = new SpuBoundsVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

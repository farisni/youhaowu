package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SpuInfoDescVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.SpuInfoDescEntity;
import com.youhaowu.product.mapper.SpuInfoDescMapper;
import com.youhaowu.product.service.SpuInfoDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
/**
 * SpuInfoDesc Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SpuInfoDescServiceImpl implements SpuInfoDescService {

    @Autowired
    private SpuInfoDescMapper spuInfoDescMapper;
    @Override
    public PageData<SpuInfoDescVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuInfoDescMapper, new LambdaQueryWrapper<>(), query, e -> {
        SpuInfoDescVO vo = new SpuInfoDescVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuInfoDescVO getById(Long id) {
        SpuInfoDescEntity e = spuInfoDescMapper.selectById(id);
        if (e == null) return null;
        SpuInfoDescVO vo = new SpuInfoDescVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public Integer save(SpuInfoDescVO vo) {
        SpuInfoDescEntity e = new SpuInfoDescEntity();
        BeanUtil.copyProperties(vo, e);
        return spuInfoDescMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SpuInfoDescVO> list) {
        List<SpuInfoDescEntity> entities = list.stream().map(vo -> {
            SpuInfoDescEntity e = new SpuInfoDescEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return spuInfoDescMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(SpuInfoDescVO vo) {
        SpuInfoDescEntity e = new SpuInfoDescEntity();
        BeanUtil.copyProperties(vo, e);
        return spuInfoDescMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return spuInfoDescMapper.deleteByIds(ids);
    }
    @Override
    public Integer saveSpuInfoDesc(SpuInfoDescVO descVO) {
        SpuInfoDescEntity e = new SpuInfoDescEntity();
        BeanUtil.copyProperties(descVO, e);
        return spuInfoDescMapper.insert(e);
    }
}

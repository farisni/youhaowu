package com.youhaowu.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.entity.WareSkuEntity;
import com.youhaowu.ware.mapper.WareSkuMapper;
import com.youhaowu.ware.dto.WareSkuQueryDTO;
import com.youhaowu.ware.service.WareSkuService;
import com.youhaowu.ware.vo.WareSkuVO;
import com.youhaowu.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import com.youhaowu.common.vo.SkuHasStockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class WareSkuServiceImpl implements WareSkuService {

    @Autowired
    private WareSkuMapper wareSkuMapper;

    @Override
    public List<SkuHasStockVO> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVO> result = new ArrayList<>();
        if (skuIds != null) {
            for (Long skuId : skuIds) {
                SkuHasStockVO vo = new SkuHasStockVO();
                vo.setSkuId(skuId);
                Long count = wareSkuMapper.selectCount(
                    new LambdaQueryWrapper<WareSkuEntity>().eq(WareSkuEntity::getSkuId, skuId));
                vo.setHasStock(count != null && count > 0);
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public PageData<WareSkuVO> page(WareSkuQueryDTO query) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(wareSkuMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public WareSkuVO getById(Long id) {
        WareSkuEntity e = wareSkuMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(WareSkuVO vo) {
        WareSkuEntity e = new WareSkuEntity();
        BeanUtil.copyProperties(vo, e);
        return wareSkuMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<WareSkuVO> list) {
        List<WareSkuEntity> entities = list.stream().map(vo -> {
            WareSkuEntity e = new WareSkuEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return wareSkuMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, WareSkuVO vo) {
        WareSkuEntity e = new WareSkuEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return wareSkuMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return wareSkuMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return wareSkuMapper.deleteByIds(ids);
    }

    private WareSkuVO entityToVO(WareSkuEntity e) {
        WareSkuVO v = new WareSkuVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

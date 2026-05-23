package com.wheatmall.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.entity.WareInfoEntity;
import com.wheatmall.ware.mapper.WareInfoMapper;
import com.wheatmall.ware.query.WareInfoQueryDTO;
import com.wheatmall.ware.service.WareInfoService;
import com.wheatmall.ware.vo.WareInfoVO;
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
public class WareInfoServiceImpl implements WareInfoService {

    @Autowired
    private WareInfoMapper wareInfoMapper;

    @Override
    public PageData<WareInfoVO> page(WareInfoQueryDTO query) {
        LambdaQueryWrapper<WareInfoEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(wareInfoMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public WareInfoVO getById(Long id) {
        WareInfoEntity e = wareInfoMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(WareInfoVO vo) {
        WareInfoEntity e = new WareInfoEntity();
        BeanUtil.copyProperties(vo, e);
        return wareInfoMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<WareInfoVO> list) {
        List<WareInfoEntity> entities = list.stream().map(vo -> {
            WareInfoEntity e = new WareInfoEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return wareInfoMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, WareInfoVO vo) {
        WareInfoEntity e = new WareInfoEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return wareInfoMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return wareInfoMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return wareInfoMapper.deleteBatchIds(ids);
    }

    private WareInfoVO entityToVO(WareInfoEntity e) {
        WareInfoVO v = new WareInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

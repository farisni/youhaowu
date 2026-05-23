package com.wheatmall.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.entity.WareOrderTaskDetailEntity;
import com.wheatmall.ware.mapper.WareOrderTaskDetailMapper;
import com.wheatmall.ware.dto.WareOrderTaskDetailQueryDTO;
import com.wheatmall.ware.service.WareOrderTaskDetailService;
import com.wheatmall.ware.vo.WareOrderTaskDetailVO;
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
public class WareOrderTaskDetailServiceImpl implements WareOrderTaskDetailService {

    @Autowired
    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Override
    public PageData<WareOrderTaskDetailVO> page(WareOrderTaskDetailQueryDTO query) {
        LambdaQueryWrapper<WareOrderTaskDetailEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(wareOrderTaskDetailMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public WareOrderTaskDetailVO getById(Long id) {
        WareOrderTaskDetailEntity e = wareOrderTaskDetailMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(WareOrderTaskDetailVO vo) {
        WareOrderTaskDetailEntity e = new WareOrderTaskDetailEntity();
        BeanUtil.copyProperties(vo, e);
        return wareOrderTaskDetailMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<WareOrderTaskDetailVO> list) {
        List<WareOrderTaskDetailEntity> entities = list.stream().map(vo -> {
            WareOrderTaskDetailEntity e = new WareOrderTaskDetailEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return wareOrderTaskDetailMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, WareOrderTaskDetailVO vo) {
        WareOrderTaskDetailEntity e = new WareOrderTaskDetailEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return wareOrderTaskDetailMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return wareOrderTaskDetailMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return wareOrderTaskDetailMapper.deleteBatchIds(ids);
    }

    private WareOrderTaskDetailVO entityToVO(WareOrderTaskDetailEntity e) {
        WareOrderTaskDetailVO v = new WareOrderTaskDetailVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

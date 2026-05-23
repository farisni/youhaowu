package com.wheatmall.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.entity.WareOrderTaskEntity;
import com.wheatmall.ware.mapper.WareOrderTaskMapper;
import com.wheatmall.ware.dto.WareOrderTaskQueryDTO;
import com.wheatmall.ware.service.WareOrderTaskService;
import com.wheatmall.ware.vo.WareOrderTaskVO;
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
public class WareOrderTaskServiceImpl implements WareOrderTaskService {

    @Autowired
    private WareOrderTaskMapper wareOrderTaskMapper;

    @Override
    public PageData<WareOrderTaskVO> page(WareOrderTaskQueryDTO query) {
        LambdaQueryWrapper<WareOrderTaskEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(wareOrderTaskMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public WareOrderTaskVO getById(Long id) {
        WareOrderTaskEntity e = wareOrderTaskMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(WareOrderTaskVO vo) {
        WareOrderTaskEntity e = new WareOrderTaskEntity();
        BeanUtil.copyProperties(vo, e);
        return wareOrderTaskMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<WareOrderTaskVO> list) {
        List<WareOrderTaskEntity> entities = list.stream().map(vo -> {
            WareOrderTaskEntity e = new WareOrderTaskEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return wareOrderTaskMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, WareOrderTaskVO vo) {
        WareOrderTaskEntity e = new WareOrderTaskEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return wareOrderTaskMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return wareOrderTaskMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return wareOrderTaskMapper.deleteBatchIds(ids);
    }

    private WareOrderTaskVO entityToVO(WareOrderTaskEntity e) {
        WareOrderTaskVO v = new WareOrderTaskVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

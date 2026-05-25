package com.youhaowu.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.entity.GrowthChangeHistoryEntity;
import com.youhaowu.member.mapper.GrowthChangeHistoryMapper;
import com.youhaowu.member.dto.GrowthChangeHistoryQueryDTO;
import com.youhaowu.member.service.GrowthChangeHistoryService;
import com.youhaowu.member.vo.GrowthChangeHistoryVO;
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
public class GrowthChangeHistoryServiceImpl implements GrowthChangeHistoryService {

    @Autowired
    private GrowthChangeHistoryMapper growthChangeHistoryMapper;

    @Override
    public PageData<GrowthChangeHistoryVO> page(GrowthChangeHistoryQueryDTO query) {
        LambdaQueryWrapper<GrowthChangeHistoryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(growthChangeHistoryMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public GrowthChangeHistoryVO getById(Long id) {
        GrowthChangeHistoryEntity e = growthChangeHistoryMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(GrowthChangeHistoryVO vo) {
        GrowthChangeHistoryEntity e = new GrowthChangeHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        return growthChangeHistoryMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<GrowthChangeHistoryVO> list) {
        List<GrowthChangeHistoryEntity> entities = list.stream().map(vo -> {
            GrowthChangeHistoryEntity e = new GrowthChangeHistoryEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return growthChangeHistoryMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, GrowthChangeHistoryVO vo) {
        GrowthChangeHistoryEntity e = new GrowthChangeHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return growthChangeHistoryMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return growthChangeHistoryMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return growthChangeHistoryMapper.deleteByIds(ids);
    }

    private GrowthChangeHistoryVO entityToVO(GrowthChangeHistoryEntity e) {
        GrowthChangeHistoryVO v = new GrowthChangeHistoryVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

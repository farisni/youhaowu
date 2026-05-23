package com.wheatmall.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.entity.IntegrationChangeHistoryEntity;
import com.wheatmall.member.mapper.IntegrationChangeHistoryMapper;
import com.wheatmall.member.dto.IntegrationChangeHistoryQueryDTO;
import com.wheatmall.member.service.IntegrationChangeHistoryService;
import com.wheatmall.member.vo.IntegrationChangeHistoryVO;
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
public class IntegrationChangeHistoryServiceImpl implements IntegrationChangeHistoryService {

    @Autowired
    private IntegrationChangeHistoryMapper integrationChangeHistoryMapper;

    @Override
    public PageData<IntegrationChangeHistoryVO> page(IntegrationChangeHistoryQueryDTO query) {
        LambdaQueryWrapper<IntegrationChangeHistoryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(integrationChangeHistoryMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public IntegrationChangeHistoryVO getById(Long id) {
        IntegrationChangeHistoryEntity e = integrationChangeHistoryMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(IntegrationChangeHistoryVO vo) {
        IntegrationChangeHistoryEntity e = new IntegrationChangeHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        return integrationChangeHistoryMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<IntegrationChangeHistoryVO> list) {
        List<IntegrationChangeHistoryEntity> entities = list.stream().map(vo -> {
            IntegrationChangeHistoryEntity e = new IntegrationChangeHistoryEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return integrationChangeHistoryMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, IntegrationChangeHistoryVO vo) {
        IntegrationChangeHistoryEntity e = new IntegrationChangeHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return integrationChangeHistoryMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return integrationChangeHistoryMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return integrationChangeHistoryMapper.deleteBatchIds(ids);
    }

    private IntegrationChangeHistoryVO entityToVO(IntegrationChangeHistoryEntity e) {
        IntegrationChangeHistoryVO v = new IntegrationChangeHistoryVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

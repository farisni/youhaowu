package com.youhaowu.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.cms.entity.NewsEntity;
import com.youhaowu.cms.mapper.NewsMapper;
import com.youhaowu.cms.dto.NewsQueryDTO;
import com.youhaowu.cms.dto.NewsSaveDTO;
import com.youhaowu.cms.service.NewsService;
import com.youhaowu.cms.vo.NewsVO;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<NewsVO> listEnabled() {
        LambdaQueryWrapper<NewsEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(NewsEntity::getStatus, 1).orderByAsc(NewsEntity::getSort);
        return newsMapper.selectList(qw).stream().map(e -> {
            NewsVO vo = new NewsVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public NewsVO getById(Long id) {
        NewsEntity e = newsMapper.selectById(id);
        if (e == null) return null;
        NewsVO vo = new NewsVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public PageData<NewsVO> page(NewsQueryDTO query) {
        LambdaQueryWrapper<NewsEntity> qw = new LambdaQueryWrapper<>();
        if (query.getTitle() != null) qw.like(NewsEntity::getTitle, query.getTitle());
        if (query.getStatus() != null) qw.eq(NewsEntity::getStatus, query.getStatus());
        qw.orderByAsc(NewsEntity::getSort);
        return PageUtils.selectPage(newsMapper, qw, query, e -> {
            NewsVO vo = new NewsVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(NewsSaveDTO dto) {
        NewsEntity e = new NewsEntity();
        BeanUtil.copyProperties(dto, e);
        return newsMapper.insert(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(Long id, NewsSaveDTO dto) {
        NewsEntity e = new NewsEntity();
        BeanUtil.copyProperties(dto, e);
        e.setId(id);
        return newsMapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> ids) {
        return newsMapper.deleteByIds(ids);
    }
}

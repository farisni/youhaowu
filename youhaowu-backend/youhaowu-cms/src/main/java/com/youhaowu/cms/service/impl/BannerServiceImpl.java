package com.youhaowu.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.cms.entity.BannerEntity;
import com.youhaowu.cms.mapper.BannerMapper;
import com.youhaowu.cms.dto.BannerQueryDTO;
import com.youhaowu.cms.dto.BannerSaveDTO;
import com.youhaowu.cms.service.BannerService;
import com.youhaowu.cms.vo.BannerVO;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVO> listEnabled() {
        LambdaQueryWrapper<BannerEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(BannerEntity::getStatus, 1).orderByAsc(BannerEntity::getSort);
        return bannerMapper.selectList(qw).stream().map(e -> {
            BannerVO vo = new BannerVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public PageData<BannerVO> page(BannerQueryDTO query) {
        LambdaQueryWrapper<BannerEntity> qw = new LambdaQueryWrapper<>();
        if (query.getTitle() != null) qw.like(BannerEntity::getTitle, query.getTitle());
        if (query.getStatus() != null) qw.eq(BannerEntity::getStatus, query.getStatus());
        qw.orderByAsc(BannerEntity::getSort);
        return PageUtils.selectPage(bannerMapper, qw, query, e -> {
            BannerVO vo = new BannerVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(BannerSaveDTO dto) {
        BannerEntity e = new BannerEntity();
        BeanUtil.copyProperties(dto, e);
        return bannerMapper.insert(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(Long id, BannerSaveDTO dto) {
        BannerEntity e = new BannerEntity();
        BeanUtil.copyProperties(dto, e);
        e.setId(id);
        return bannerMapper.updateById(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> ids) {
        return bannerMapper.deleteByIds(ids);
    }
}

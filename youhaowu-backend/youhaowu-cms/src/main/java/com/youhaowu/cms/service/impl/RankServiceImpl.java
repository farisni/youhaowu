package com.youhaowu.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.cms.entity.RankEntity;
import com.youhaowu.cms.mapper.RankMapper;
import com.youhaowu.cms.dto.RankQueryDTO;
import com.youhaowu.cms.dto.RankSaveDTO;
import com.youhaowu.cms.service.RankService;
import com.youhaowu.cms.vo.RankVO;
import com.youhaowu.common.config.MinioProperties;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private MinioProperties minioProperties;

    private String fullUrl(String path) {
        if (StrUtil.isBlank(path)) return path;
        if (path.startsWith("http")) return path;
        return minioProperties.getEndpoint() + path;
    }

    @Override
    public List<RankVO> listByTab(String tabType) {
        LambdaQueryWrapper<RankEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(RankEntity::getStatus, 1)
          .eq(RankEntity::getTabType, tabType)
          .orderByAsc(RankEntity::getSort);
        return rankMapper.selectList(qw).stream().map(e -> {
            RankVO vo = new RankVO();
            BeanUtil.copyProperties(e, vo);
            vo.setImgUrl(fullUrl(vo.getImgUrl()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public RankVO getById(Long id) {
        RankEntity e = rankMapper.selectById(id);
        if (e == null) return null;
        RankVO vo = new RankVO();
        BeanUtil.copyProperties(e, vo);
        vo.setImgUrl(fullUrl(vo.getImgUrl()));
        return vo;
    }

    @Override
    public PageData<RankVO> page(RankQueryDTO query) {
        LambdaQueryWrapper<RankEntity> qw = new LambdaQueryWrapper<>();
        if (query.getTabType() != null) qw.eq(RankEntity::getTabType, query.getTabType());
        if (query.getStatus() != null) qw.eq(RankEntity::getStatus, query.getStatus());
        qw.orderByAsc(RankEntity::getSort);
        return PageUtils.selectPage(rankMapper, qw, query, e -> {
            RankVO vo = new RankVO();
            BeanUtil.copyProperties(e, vo);
            vo.setImgUrl(fullUrl(vo.getImgUrl()));
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(RankSaveDTO dto) {
        if (dto.id() != null) {
            RankEntity existing = rankMapper.selectById(dto.id());
            if (existing == null) {
                throw new RuntimeException("排行记录不存在: " + dto.id());
            }
            BeanUtil.copyProperties(dto, existing);
            return rankMapper.updateById(existing);
        }
        RankEntity e = new RankEntity();
        BeanUtil.copyProperties(dto, e);
        return rankMapper.insert(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> ids) {
        return rankMapper.deleteByIds(ids);
    }
}

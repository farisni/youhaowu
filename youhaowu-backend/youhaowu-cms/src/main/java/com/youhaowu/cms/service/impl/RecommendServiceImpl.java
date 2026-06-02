package com.youhaowu.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.cms.entity.RecommendEntity;
import com.youhaowu.cms.mapper.RecommendMapper;
import com.youhaowu.cms.dto.RecommendQueryDTO;
import com.youhaowu.cms.dto.RecommendSaveDTO;
import com.youhaowu.cms.service.RecommendService;
import com.youhaowu.cms.vo.RecommendVO;
import com.youhaowu.common.config.MinioProperties;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecommendMapper recommendMapper;

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 将相对路径拼成完整 MinIO URL
     */
    private String fullUrl(String path) {
        if (StrUtil.isBlank(path)) return path;
        if (path.startsWith("http")) return path;
        return minioProperties.getEndpoint() + path;
    }

    @Override
    public List<RecommendVO> listEnabled() {
        LambdaQueryWrapper<RecommendEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(RecommendEntity::getStatus, 1).orderByAsc(RecommendEntity::getSort);
        return recommendMapper.selectList(qw).stream().map(e -> {
            RecommendVO vo = new RecommendVO();
            BeanUtil.copyProperties(e, vo);
            vo.setImgUrl(fullUrl(vo.getImgUrl()));
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public RecommendVO getById(Long id) {
        RecommendEntity e = recommendMapper.selectById(id);
        if (e == null) return null;
        RecommendVO vo = new RecommendVO();
        BeanUtil.copyProperties(e, vo);
        vo.setImgUrl(fullUrl(vo.getImgUrl()));
        return vo;
    }

    @Override
    public PageData<RecommendVO> page(RecommendQueryDTO query) {
        LambdaQueryWrapper<RecommendEntity> qw = new LambdaQueryWrapper<>();
        if (query.getStatus() != null) qw.eq(RecommendEntity::getStatus, query.getStatus());
        qw.orderByAsc(RecommendEntity::getSort);
        return PageUtils.selectPage(recommendMapper, qw, query, e -> {
            RecommendVO vo = new RecommendVO();
            BeanUtil.copyProperties(e, vo);
            vo.setImgUrl(fullUrl(vo.getImgUrl()));
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(RecommendSaveDTO dto) {
        //  id 非空：更新；id 空：新增
        if (dto.getId() != null) {
            RecommendEntity existing = recommendMapper.selectById(dto.getId());
            if (existing == null) {
                throw new RuntimeException("推荐记录不存在: " + dto.getId());
            }
            BeanUtil.copyProperties(dto, existing);
            return recommendMapper.updateById(existing);
        }
        RecommendEntity e = new RecommendEntity();
        BeanUtil.copyProperties(dto, e);
        return recommendMapper.insert(e);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBatch(List<Long> ids) {
        return recommendMapper.deleteByIds(ids);
    }
}

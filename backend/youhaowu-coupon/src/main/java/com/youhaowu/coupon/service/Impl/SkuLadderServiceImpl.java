package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.SkuLadderEntity;
import com.youhaowu.coupon.mapper.SkuLadderMapper;
import com.youhaowu.coupon.dto.SkuLadderQueryDTO;
import com.youhaowu.coupon.service.SkuLadderService;
import com.youhaowu.coupon.vo.SkuLadderVO;
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
public class SkuLadderServiceImpl implements SkuLadderService {

    @Autowired
    private SkuLadderMapper skuLadderMapper;

    @Override
    public PageData<SkuLadderVO> page(SkuLadderQueryDTO query) {
        LambdaQueryWrapper<SkuLadderEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(skuLadderMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SkuLadderVO getById(Long id) {
        SkuLadderEntity e = skuLadderMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SkuLadderVO vo) {
        SkuLadderEntity e = new SkuLadderEntity();
        BeanUtil.copyProperties(vo, e);
        return skuLadderMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SkuLadderVO> list) {
        List<SkuLadderEntity> entities = list.stream().map(vo -> {
            SkuLadderEntity e = new SkuLadderEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return skuLadderMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SkuLadderVO vo) {
        SkuLadderEntity e = new SkuLadderEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return skuLadderMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return skuLadderMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return skuLadderMapper.deleteBatchIds(ids);
    }

    private SkuLadderVO entityToVO(SkuLadderEntity e) {
        SkuLadderVO v = new SkuLadderVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

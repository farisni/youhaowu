package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.CouponSpuCategoryRelationEntity;
import com.youhaowu.coupon.mapper.CouponSpuCategoryRelationMapper;
import com.youhaowu.coupon.dto.CouponSpuCategoryRelationQueryDTO;
import com.youhaowu.coupon.service.CouponSpuCategoryRelationService;
import com.youhaowu.coupon.vo.CouponSpuCategoryRelationVO;
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
public class CouponSpuCategoryRelationServiceImpl implements CouponSpuCategoryRelationService {

    @Autowired
    private CouponSpuCategoryRelationMapper couponSpuCategoryRelationMapper;

    @Override
    public PageData<CouponSpuCategoryRelationVO> page(CouponSpuCategoryRelationQueryDTO query) {
        LambdaQueryWrapper<CouponSpuCategoryRelationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(couponSpuCategoryRelationMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public CouponSpuCategoryRelationVO getById(Long id) {
        CouponSpuCategoryRelationEntity e = couponSpuCategoryRelationMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(CouponSpuCategoryRelationVO vo) {
        CouponSpuCategoryRelationEntity e = new CouponSpuCategoryRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return couponSpuCategoryRelationMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CouponSpuCategoryRelationVO> list) {
        List<CouponSpuCategoryRelationEntity> entities = list.stream().map(vo -> {
            CouponSpuCategoryRelationEntity e = new CouponSpuCategoryRelationEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return couponSpuCategoryRelationMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, CouponSpuCategoryRelationVO vo) {
        CouponSpuCategoryRelationEntity e = new CouponSpuCategoryRelationEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return couponSpuCategoryRelationMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return couponSpuCategoryRelationMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return couponSpuCategoryRelationMapper.deleteByIds(ids);
    }

    private CouponSpuCategoryRelationVO entityToVO(CouponSpuCategoryRelationEntity e) {
        CouponSpuCategoryRelationVO v = new CouponSpuCategoryRelationVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

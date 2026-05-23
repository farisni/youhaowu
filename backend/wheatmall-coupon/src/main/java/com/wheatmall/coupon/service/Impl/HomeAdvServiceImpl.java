package com.wheatmall.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.entity.HomeAdvEntity;
import com.wheatmall.coupon.mapper.HomeAdvMapper;
import com.wheatmall.coupon.dto.HomeAdvQueryDTO;
import com.wheatmall.coupon.service.HomeAdvService;
import com.wheatmall.coupon.vo.HomeAdvVO;
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
public class HomeAdvServiceImpl implements HomeAdvService {

    @Autowired
    private HomeAdvMapper homeAdvMapper;

    @Override
    public PageData<HomeAdvVO> page(HomeAdvQueryDTO query) {
        LambdaQueryWrapper<HomeAdvEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(homeAdvMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public HomeAdvVO getById(Long id) {
        HomeAdvEntity e = homeAdvMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(HomeAdvVO vo) {
        HomeAdvEntity e = new HomeAdvEntity();
        BeanUtil.copyProperties(vo, e);
        return homeAdvMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<HomeAdvVO> list) {
        List<HomeAdvEntity> entities = list.stream().map(vo -> {
            HomeAdvEntity e = new HomeAdvEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return homeAdvMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, HomeAdvVO vo) {
        HomeAdvEntity e = new HomeAdvEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return homeAdvMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return homeAdvMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return homeAdvMapper.deleteBatchIds(ids);
    }

    private HomeAdvVO entityToVO(HomeAdvEntity e) {
        HomeAdvVO v = new HomeAdvVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

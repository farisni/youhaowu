package com.wheatmall.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.entity.HomeSubjectSpuEntity;
import com.wheatmall.coupon.mapper.HomeSubjectSpuMapper;
import com.wheatmall.coupon.dto.HomeSubjectSpuQueryDTO;
import com.wheatmall.coupon.service.HomeSubjectSpuService;
import com.wheatmall.coupon.vo.HomeSubjectSpuVO;
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
public class HomeSubjectSpuServiceImpl implements HomeSubjectSpuService {

    @Autowired
    private HomeSubjectSpuMapper homeSubjectSpuMapper;

    @Override
    public PageData<HomeSubjectSpuVO> page(HomeSubjectSpuQueryDTO query) {
        LambdaQueryWrapper<HomeSubjectSpuEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(homeSubjectSpuMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public HomeSubjectSpuVO getById(Long id) {
        HomeSubjectSpuEntity e = homeSubjectSpuMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(HomeSubjectSpuVO vo) {
        HomeSubjectSpuEntity e = new HomeSubjectSpuEntity();
        BeanUtil.copyProperties(vo, e);
        return homeSubjectSpuMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<HomeSubjectSpuVO> list) {
        List<HomeSubjectSpuEntity> entities = list.stream().map(vo -> {
            HomeSubjectSpuEntity e = new HomeSubjectSpuEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return homeSubjectSpuMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, HomeSubjectSpuVO vo) {
        HomeSubjectSpuEntity e = new HomeSubjectSpuEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return homeSubjectSpuMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return homeSubjectSpuMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return homeSubjectSpuMapper.deleteBatchIds(ids);
    }

    private HomeSubjectSpuVO entityToVO(HomeSubjectSpuEntity e) {
        HomeSubjectSpuVO v = new HomeSubjectSpuVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

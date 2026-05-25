package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.HomeSubjectEntity;
import com.youhaowu.coupon.mapper.HomeSubjectMapper;
import com.youhaowu.coupon.dto.HomeSubjectQueryDTO;
import com.youhaowu.coupon.service.HomeSubjectService;
import com.youhaowu.coupon.vo.HomeSubjectVO;
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
public class HomeSubjectServiceImpl implements HomeSubjectService {

    @Autowired
    private HomeSubjectMapper homeSubjectMapper;

    @Override
    public PageData<HomeSubjectVO> page(HomeSubjectQueryDTO query) {
        LambdaQueryWrapper<HomeSubjectEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(homeSubjectMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public HomeSubjectVO getById(Long id) {
        HomeSubjectEntity e = homeSubjectMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(HomeSubjectVO vo) {
        HomeSubjectEntity e = new HomeSubjectEntity();
        BeanUtil.copyProperties(vo, e);
        return homeSubjectMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<HomeSubjectVO> list) {
        List<HomeSubjectEntity> entities = list.stream().map(vo -> {
            HomeSubjectEntity e = new HomeSubjectEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return homeSubjectMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, HomeSubjectVO vo) {
        HomeSubjectEntity e = new HomeSubjectEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return homeSubjectMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return homeSubjectMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return homeSubjectMapper.deleteByIds(ids);
    }

    private HomeSubjectVO entityToVO(HomeSubjectEntity e) {
        HomeSubjectVO v = new HomeSubjectVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

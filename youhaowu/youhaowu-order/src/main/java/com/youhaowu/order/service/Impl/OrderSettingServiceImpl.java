package com.youhaowu.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.entity.OrderSettingEntity;
import com.youhaowu.order.mapper.OrderSettingMapper;
import com.youhaowu.order.dto.OrderSettingQueryDTO;
import com.youhaowu.order.service.OrderSettingService;
import com.youhaowu.order.vo.OrderSettingVO;
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
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public PageData<OrderSettingVO> page(OrderSettingQueryDTO query) {
        LambdaQueryWrapper<OrderSettingEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderSettingMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderSettingVO getById(Long id) {
        OrderSettingEntity e = orderSettingMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderSettingVO vo) {
        OrderSettingEntity e = new OrderSettingEntity();
        BeanUtil.copyProperties(vo, e);
        return orderSettingMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderSettingVO> list) {
        List<OrderSettingEntity> entities = list.stream().map(vo -> {
            OrderSettingEntity e = new OrderSettingEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderSettingMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderSettingVO vo) {
        OrderSettingEntity e = new OrderSettingEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderSettingMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderSettingMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderSettingMapper.deleteBatchIds(ids);
    }

    private OrderSettingVO entityToVO(OrderSettingEntity e) {
        OrderSettingVO v = new OrderSettingVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

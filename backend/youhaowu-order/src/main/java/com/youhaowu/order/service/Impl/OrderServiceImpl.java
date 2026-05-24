package com.youhaowu.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.entity.OrderEntity;
import com.youhaowu.order.mapper.OrderMapper;
import com.youhaowu.order.dto.OrderQueryDTO;
import com.youhaowu.order.service.OrderService;
import com.youhaowu.order.vo.OrderVO;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageData<OrderVO> page(OrderQueryDTO query) {
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderVO getById(Long id) {
        OrderEntity e = orderMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderVO vo) {
        OrderEntity e = new OrderEntity();
        BeanUtil.copyProperties(vo, e);
        return orderMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderVO> list) {
        List<OrderEntity> entities = list.stream().map(vo -> {
            OrderEntity e = new OrderEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderVO vo) {
        OrderEntity e = new OrderEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderMapper.deleteBatchIds(ids);
    }

    private OrderVO entityToVO(OrderEntity e) {
        OrderVO v = new OrderVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

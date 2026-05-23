package com.wheatmall.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.entity.OrderItemEntity;
import com.wheatmall.order.mapper.OrderItemMapper;
import com.wheatmall.order.query.OrderItemQueryDTO;
import com.wheatmall.order.service.OrderItemService;
import com.wheatmall.order.vo.OrderItemVO;
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
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public PageData<OrderItemVO> page(OrderItemQueryDTO query) {
        LambdaQueryWrapper<OrderItemEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderItemMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderItemVO getById(Long id) {
        OrderItemEntity e = orderItemMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderItemVO vo) {
        OrderItemEntity e = new OrderItemEntity();
        BeanUtil.copyProperties(vo, e);
        return orderItemMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderItemVO> list) {
        List<OrderItemEntity> entities = list.stream().map(vo -> {
            OrderItemEntity e = new OrderItemEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderItemMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderItemVO vo) {
        OrderItemEntity e = new OrderItemEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderItemMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderItemMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderItemMapper.deleteBatchIds(ids);
    }

    private OrderItemVO entityToVO(OrderItemEntity e) {
        OrderItemVO v = new OrderItemVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

package com.wheatmall.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.entity.OrderOperateHistoryEntity;
import com.wheatmall.order.mapper.OrderOperateHistoryMapper;
import com.wheatmall.order.dto.OrderOperateHistoryQueryDTO;
import com.wheatmall.order.service.OrderOperateHistoryService;
import com.wheatmall.order.vo.OrderOperateHistoryVO;
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
public class OrderOperateHistoryServiceImpl implements OrderOperateHistoryService {

    @Autowired
    private OrderOperateHistoryMapper orderOperateHistoryMapper;

    @Override
    public PageData<OrderOperateHistoryVO> page(OrderOperateHistoryQueryDTO query) {
        LambdaQueryWrapper<OrderOperateHistoryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderOperateHistoryMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderOperateHistoryVO getById(Long id) {
        OrderOperateHistoryEntity e = orderOperateHistoryMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderOperateHistoryVO vo) {
        OrderOperateHistoryEntity e = new OrderOperateHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        return orderOperateHistoryMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderOperateHistoryVO> list) {
        List<OrderOperateHistoryEntity> entities = list.stream().map(vo -> {
            OrderOperateHistoryEntity e = new OrderOperateHistoryEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderOperateHistoryMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderOperateHistoryVO vo) {
        OrderOperateHistoryEntity e = new OrderOperateHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderOperateHistoryMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderOperateHistoryMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderOperateHistoryMapper.deleteBatchIds(ids);
    }

    private OrderOperateHistoryVO entityToVO(OrderOperateHistoryEntity e) {
        OrderOperateHistoryVO v = new OrderOperateHistoryVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

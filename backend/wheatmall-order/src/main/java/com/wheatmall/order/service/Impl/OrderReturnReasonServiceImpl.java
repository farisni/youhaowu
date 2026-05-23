package com.wheatmall.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.entity.OrderReturnReasonEntity;
import com.wheatmall.order.mapper.OrderReturnReasonMapper;
import com.wheatmall.order.dto.OrderReturnReasonQueryDTO;
import com.wheatmall.order.service.OrderReturnReasonService;
import com.wheatmall.order.vo.OrderReturnReasonVO;
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
public class OrderReturnReasonServiceImpl implements OrderReturnReasonService {

    @Autowired
    private OrderReturnReasonMapper orderReturnReasonMapper;

    @Override
    public PageData<OrderReturnReasonVO> page(OrderReturnReasonQueryDTO query) {
        LambdaQueryWrapper<OrderReturnReasonEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderReturnReasonMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderReturnReasonVO getById(Long id) {
        OrderReturnReasonEntity e = orderReturnReasonMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderReturnReasonVO vo) {
        OrderReturnReasonEntity e = new OrderReturnReasonEntity();
        BeanUtil.copyProperties(vo, e);
        return orderReturnReasonMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderReturnReasonVO> list) {
        List<OrderReturnReasonEntity> entities = list.stream().map(vo -> {
            OrderReturnReasonEntity e = new OrderReturnReasonEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderReturnReasonMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderReturnReasonVO vo) {
        OrderReturnReasonEntity e = new OrderReturnReasonEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderReturnReasonMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderReturnReasonMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderReturnReasonMapper.deleteBatchIds(ids);
    }

    private OrderReturnReasonVO entityToVO(OrderReturnReasonEntity e) {
        OrderReturnReasonVO v = new OrderReturnReasonVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

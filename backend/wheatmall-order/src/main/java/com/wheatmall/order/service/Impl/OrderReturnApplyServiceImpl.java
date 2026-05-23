package com.wheatmall.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.entity.OrderReturnApplyEntity;
import com.wheatmall.order.mapper.OrderReturnApplyMapper;
import com.wheatmall.order.query.OrderReturnApplyQueryDTO;
import com.wheatmall.order.service.OrderReturnApplyService;
import com.wheatmall.order.vo.OrderReturnApplyVO;
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
public class OrderReturnApplyServiceImpl implements OrderReturnApplyService {

    @Autowired
    private OrderReturnApplyMapper orderReturnApplyMapper;

    @Override
    public PageData<OrderReturnApplyVO> page(OrderReturnApplyQueryDTO query) {
        LambdaQueryWrapper<OrderReturnApplyEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(orderReturnApplyMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public OrderReturnApplyVO getById(Long id) {
        OrderReturnApplyEntity e = orderReturnApplyMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(OrderReturnApplyVO vo) {
        OrderReturnApplyEntity e = new OrderReturnApplyEntity();
        BeanUtil.copyProperties(vo, e);
        return orderReturnApplyMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<OrderReturnApplyVO> list) {
        List<OrderReturnApplyEntity> entities = list.stream().map(vo -> {
            OrderReturnApplyEntity e = new OrderReturnApplyEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return orderReturnApplyMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, OrderReturnApplyVO vo) {
        OrderReturnApplyEntity e = new OrderReturnApplyEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return orderReturnApplyMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return orderReturnApplyMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return orderReturnApplyMapper.deleteBatchIds(ids);
    }

    private OrderReturnApplyVO entityToVO(OrderReturnApplyEntity e) {
        OrderReturnApplyVO v = new OrderReturnApplyVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

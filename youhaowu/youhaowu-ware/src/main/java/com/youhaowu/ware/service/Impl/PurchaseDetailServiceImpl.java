package com.youhaowu.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.entity.PurchaseDetailEntity;
import com.youhaowu.ware.mapper.PurchaseDetailMapper;
import com.youhaowu.ware.dto.PurchaseDetailQueryDTO;
import com.youhaowu.ware.service.PurchaseDetailService;
import com.youhaowu.ware.vo.PurchaseDetailVO;
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
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;

    @Override
    public PageData<PurchaseDetailVO> page(PurchaseDetailQueryDTO query) {
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(purchaseDetailMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public PurchaseDetailVO getById(Long id) {
        PurchaseDetailEntity e = purchaseDetailMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(PurchaseDetailVO vo) {
        PurchaseDetailEntity e = new PurchaseDetailEntity();
        BeanUtil.copyProperties(vo, e);
        return purchaseDetailMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<PurchaseDetailVO> list) {
        List<PurchaseDetailEntity> entities = list.stream().map(vo -> {
            PurchaseDetailEntity e = new PurchaseDetailEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return purchaseDetailMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, PurchaseDetailVO vo) {
        PurchaseDetailEntity e = new PurchaseDetailEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return purchaseDetailMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return purchaseDetailMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return purchaseDetailMapper.deleteBatchIds(ids);
    }

    private PurchaseDetailVO entityToVO(PurchaseDetailEntity e) {
        PurchaseDetailVO v = new PurchaseDetailVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

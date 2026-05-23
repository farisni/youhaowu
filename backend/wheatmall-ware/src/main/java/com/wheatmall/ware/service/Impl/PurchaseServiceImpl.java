package com.wheatmall.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.entity.PurchaseEntity;
import com.wheatmall.ware.mapper.PurchaseMapper;
import com.wheatmall.ware.dto.PurchaseQueryDTO;
import com.wheatmall.ware.service.PurchaseService;
import com.wheatmall.ware.vo.PurchaseVO;
import com.wheatmall.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wheatmall.ware.mapper.PurchaseDetailMapper;
import com.wheatmall.ware.entity.PurchaseDetailEntity;
import com.wheatmall.ware.vo.PurchaseDoneVo;
import com.wheatmall.ware.vo.MergeVo;
import com.wheatmall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;

    @Override
    public PageData<PurchaseVO> queryPageUnreceive(PurchaseQueryDTO query) {
        LambdaQueryWrapper<PurchaseEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PurchaseEntity::getStatus, 0);
        return PageUtils.selectPage(purchaseMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public void mergePurchase(MergeVo mergeVo) {
        //  合并采购需求到采购单
        if (mergeVo.getPurchaseId() == null) {
            PurchaseEntity entity = new PurchaseEntity();
            entity.setStatus(0);
            purchaseMapper.insert(entity);
            mergeVo.setPurchaseId(entity.getId());
        }
        Long purchaseId = mergeVo.getPurchaseId();
        for (Long itemId : mergeVo.getItems()) {
            PurchaseDetailEntity detail = new PurchaseDetailEntity();
            detail.setId(itemId);
            detail.setPurchaseId(purchaseId);
            detail.setStatus(1);
            purchaseDetailMapper.updateById(detail);
        }
    }

    @Override
    public void received(List<Long> ids) {
        for (Long id : ids) {
            PurchaseEntity entity = new PurchaseEntity();
            entity.setId(id);
            entity.setStatus(1);
            purchaseMapper.updateById(entity);
        }
    }

    @Override
    public void done(PurchaseDoneVo doneVo) {
        //  更新采购项状态
        if (doneVo.getItems() != null) {
            for (com.wheatmall.ware.vo.PurchaseItemDoneVo item : doneVo.getItems()) {
                PurchaseDetailEntity detail = new PurchaseDetailEntity();
                detail.setId(item.getItemId());
                detail.setStatus(item.getStatus());
                purchaseDetailMapper.updateById(detail);
            }
        }
        //  更新采购单状态
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(doneVo.getId());
        entity.setStatus(2);
        purchaseMapper.updateById(entity);
    }

    @Override
    public PageData<PurchaseVO> page(PurchaseQueryDTO query) {
        LambdaQueryWrapper<PurchaseEntity> wrapper = new LambdaQueryWrapper<>();
        return PageUtils.selectPage(purchaseMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public PurchaseVO getById(Long id) {
        PurchaseEntity e = purchaseMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(PurchaseVO vo) {
        PurchaseEntity e = new PurchaseEntity();
        BeanUtil.copyProperties(vo, e);
        return purchaseMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<PurchaseVO> list) {
        List<PurchaseEntity> entities = list.stream().map(vo -> {
            PurchaseEntity e = new PurchaseEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return purchaseMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, PurchaseVO vo) {
        PurchaseEntity e = new PurchaseEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return purchaseMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return purchaseMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return purchaseMapper.deleteBatchIds(ids);
    }

    private PurchaseVO entityToVO(PurchaseEntity e) {
        PurchaseVO v = new PurchaseVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}

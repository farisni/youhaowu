package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SpuInfoDescVO;
import com.youhaowu.common.dto.BaseQueryDTO;
import java.util.List;

/**
 * SpuInfoDesc Service
 */
public interface SpuInfoDescService {

    /** 分页查询 */
    PageData<SpuInfoDescVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuInfoDescVO getById(Long id);

    /** 保存 */
    Integer save(SpuInfoDescVO vo);

    /** 批量保存 */
    Integer saveBatch(List<SpuInfoDescVO> list);

    /** 根据ID更新 */
    Integer updateById(SpuInfoDescVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
    Integer saveSpuInfoDesc(SpuInfoDescVO descVO);
}

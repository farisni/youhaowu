package com.youhaowu.cms.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.NewsQueryDTO;
import com.youhaowu.cms.dto.NewsSaveDTO;
import com.youhaowu.cms.vo.NewsVO;

import java.util.List;

public interface NewsService {
    List<NewsVO> listEnabled();
    NewsVO getById(Long id);
    PageData<NewsVO> page(NewsQueryDTO query);
    Integer save(NewsSaveDTO dto);
    Integer update(Long id, NewsSaveDTO dto);
    Integer deleteBatch(List<Long> ids);
}

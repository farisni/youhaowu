package com.youhaowu.cms.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.NewsQueryDTO;
import com.youhaowu.cms.dto.NewsSaveDTO;
import com.youhaowu.cms.vo.NewsVO;

import java.util.List;

public interface NewsService {
    List<NewsVO> listEnabled();
    PageData<NewsVO> page(NewsQueryDTO query);
    void save(NewsSaveDTO dto);
    void update(Long id, NewsSaveDTO dto);
    void deleteBatch(List<Long> ids);
}

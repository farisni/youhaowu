package com.youhaowu.search.service;

import com.youhaowu.search.vo.SearchParam;
import com.youhaowu.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);

}

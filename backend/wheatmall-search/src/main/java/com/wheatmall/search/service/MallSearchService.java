package com.wheatmall.search.service;

import com.wheatmall.search.vo.SearchParam;
import com.wheatmall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);

}

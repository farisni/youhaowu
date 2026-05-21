package com.wheatmall.product.service;

import com.wheatmall.product.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    List<CategoryVO> listWithTree();
}

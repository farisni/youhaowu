package com.youhaowu.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.dto.CategoryQueryDTO;
import com.youhaowu.product.entity.CategoryEntity;
import com.youhaowu.product.mapper.CategoryMapper;
import com.youhaowu.product.service.Impl.CategoryServiceImpl;
import com.youhaowu.product.vo.CategoryVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryEntity cat1;
    private CategoryEntity cat2;

    @BeforeEach
    void setUp() {
        cat1 = new CategoryEntity();
        cat1.setCatId(1L);
        cat1.setName("电子产品");
        cat1.setParentCid(0L);
        cat1.setCatLevel(1);
        cat1.setSort(1);
        cat1.setShowStatus(1);

        cat2 = new CategoryEntity();
        cat2.setCatId(2L);
        cat2.setName("手机");
        cat2.setParentCid(1L);
        cat2.setCatLevel(2);
        cat2.setSort(2);
        cat2.setShowStatus(1);
    }

    @Test
    void testPageDefaultPagination() {
        CategoryQueryDTO query = new CategoryQueryDTO();
        Page<CategoryEntity> mockPage = new Page<>(1, 10);
        mockPage.setRecords(Arrays.asList(cat1, cat2));
        mockPage.setTotal(2);

        when(categoryMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class)))
                .thenReturn(mockPage);

        PageData<CategoryVO> result = categoryService.page(query);

        assertNotNull(result);
        assertEquals(2, result.getTotal());
        assertEquals(10, result.getPageSize());
        assertEquals(1, result.getPageNum());
        assertEquals(2, result.getList().size());
        assertEquals("电子产品", result.getList().get(0).getName());
        assertEquals("手机", result.getList().get(1).getName());
    }

    @Test
    void testPageCustomPagination() {
        CategoryQueryDTO query = new CategoryQueryDTO();
        query.setPageNum(2L);
        query.setPageSize(5L);

        Page<CategoryEntity> mockPage = new Page<>(2, 5);
        mockPage.setRecords(List.of(cat1));
        mockPage.setTotal(1);

        ArgumentCaptor<Page<CategoryEntity>> pageCaptor = ArgumentCaptor.forClass(Page.class);

        when(categoryMapper.selectPage(pageCaptor.capture(), any(LambdaQueryWrapper.class)))
                .thenReturn(mockPage);

        PageData<CategoryVO> result = categoryService.page(query);

        Page<CategoryEntity> capturedPage = pageCaptor.getValue();
        assertEquals(2, capturedPage.getCurrent());
        assertEquals(5, capturedPage.getSize());

        assertEquals(1, result.getTotal());
        assertEquals(5, result.getPageSize());
        assertEquals(2, result.getPageNum());
    }

    @Test
    void testPageFilterByName() {
        CategoryQueryDTO query = new CategoryQueryDTO();
        query.setName("手机");

        Page<CategoryEntity> mockPage = new Page<>(1, 10);
        mockPage.setRecords(List.of(cat2));
        mockPage.setTotal(1);

        when(categoryMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class)))
                .thenReturn(mockPage);

        PageData<CategoryVO> result = categoryService.page(query);

        assertEquals(1, result.getTotal());
        assertEquals("手机", result.getList().get(0).getName());
    }

    @Test
    void testPageEmptyResult() {
        CategoryQueryDTO query = new CategoryQueryDTO();

        Page<CategoryEntity> mockPage = new Page<>(1, 10);
        mockPage.setRecords(List.of());
        mockPage.setTotal(0);

        when(categoryMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class)))
                .thenReturn(mockPage);

        PageData<CategoryVO> result = categoryService.page(query);

        assertEquals(0, result.getTotal());
        assertTrue(result.getList().isEmpty());
    }
}

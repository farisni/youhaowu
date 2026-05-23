package com.wheatmall.product.controller;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.dto.CategoryUpdateDTO;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CategoryController 单元测试（@WebMvcTest + MockMvc + Mockito）
 * 覆盖分类树查询、分页、子分类查询、批量删除、修改五个接口
 */
@WebMvcTest(
    value = CategoryController.class,
    excludeAutoConfiguration = {
        MybatisPlusAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
    }
)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplate;

    private CategoryVO cat1;
    private CategoryVO cat1Child;

    private CategoryVO buildCategory(Long catId, String name, Long parentCid, Integer catLevel, Integer sort) {
        CategoryVO vo = new CategoryVO();
        vo.setCatId(catId);
        vo.setName(name);
        vo.setParentCid(parentCid);
        vo.setCatLevel(catLevel);
        vo.setSort(sort);
        vo.setShowStatus(1);
        vo.setIcon("icon");
        vo.setProductUnit("件");
        vo.setProductCount(0);
        return vo;
    }

    @BeforeEach
    void setUp() {
        cat1 = buildCategory(1L, "电子产品", 0L, 1, 0);
        cat1Child = buildCategory(10L, "手机", 1L, 2, 0);
        cat1.setChildren(Collections.singletonList(cat1Child));
    }

    @Test
    void testListTree() throws Exception {
        when(categoryService.listWithTree()).thenReturn(Collections.singletonList(cat1));

        mockMvc.perform(get(ProductServiceUris.ProductCategory.LIST_TREE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].catId").value(1))
                .andExpect(jsonPath("$.data[0].name").value("电子产品"))
                .andExpect(jsonPath("$.data[0].children", hasSize(1)))
                .andExpect(jsonPath("$.data[0].children[0].catId").value(10))
                .andExpect(jsonPath("$.data[0].children[0].name").value("手机"));

        verify(categoryService, times(1)).listWithTree();
    }

    @Test
    void testListTreeEmpty() throws Exception {
        when(categoryService.listWithTree()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(ProductServiceUris.ProductCategory.LIST_TREE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data", hasSize(0)));

        verify(categoryService, times(1)).listWithTree();
    }

    @Test
    void testPage() throws Exception {
        CategoryQueryDTO query = new CategoryQueryDTO();
        query.setPageNum(1L);
        query.setPageSize(10L);
        query.setName("电子");

        List<CategoryVO> list = Collections.singletonList(cat1);
        PageData<CategoryVO> pageData = PageData.of(1L, 10L, 1L, list);

        when(categoryService.page(any(CategoryQueryDTO.class))).thenReturn(pageData);

        mockMvc.perform(post(ProductServiceUris.ProductCategory.PAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.pageNum").value(1))
                .andExpect(jsonPath("$.data.list", hasSize(1)))
                .andExpect(jsonPath("$.data.list[0].catId").value(1))
                .andExpect(jsonPath("$.data.list[0].name").value("电子产品"));

        verify(categoryService, times(1)).page(any(CategoryQueryDTO.class));
    }

    @Test
    void testPageEmpty() throws Exception {
        CategoryQueryDTO query = new CategoryQueryDTO();
        PageData<CategoryVO> pageData = PageData.of(0L, 10L, 1L, Collections.emptyList());

        when(categoryService.page(any(CategoryQueryDTO.class))).thenReturn(pageData);

        mockMvc.perform(post(ProductServiceUris.ProductCategory.PAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.total").value(0))
                .andExpect(jsonPath("$.data.list", hasSize(0)));

        verify(categoryService, times(1)).page(any(CategoryQueryDTO.class));
    }

    @Test
    void testPageWithDefaults() throws Exception {
        when(categoryService.page(any(CategoryQueryDTO.class)))
                .thenReturn(PageData.of(0L, 10L, 1L, Collections.emptyList()));

        mockMvc.perform(post(ProductServiceUris.ProductCategory.PAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(categoryService, times(1)).page(any(CategoryQueryDTO.class));
    }

    @Test
    void testChildren() throws Exception {
        CategoryVO child2 = buildCategory(11L, "电脑", 1L, 2, 1);
        List<CategoryVO> children = Arrays.asList(cat1Child, child2);

        when(categoryService.getChildrenByParentId(1L)).thenReturn(children);

        mockMvc.perform(get(ProductServiceUris.ProductCategory.PARENT_BY_ID, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].catId").value(10))
                .andExpect(jsonPath("$.data[0].name").value("手机"))
                .andExpect(jsonPath("$.data[1].catId").value(11))
                .andExpect(jsonPath("$.data[1].name").value("电脑"));

        verify(categoryService, times(1)).getChildrenByParentId(1L);
    }

    @Test
    void testChildrenEmpty() throws Exception {
        when(categoryService.getChildrenByParentId(999L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(ProductServiceUris.ProductCategory.PARENT_BY_ID, 999))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data", hasSize(0)));

        verify(categoryService, times(1)).getChildrenByParentId(999L);
    }

    @Test
    void testDelete() throws Exception {
        List<Long> ids = Arrays.asList(10L, 11L, 12L);
        doNothing().when(categoryService).deleteBatch(anyList());

        mockMvc.perform(post(ProductServiceUris.ProductCategory.DELETE, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(categoryService, times(1)).deleteBatch(ids);
    }

    @Test
    void testDeleteSingle() throws Exception {
        List<Long> ids = Collections.singletonList(10L);
        doNothing().when(categoryService).deleteBatch(anyList());

        mockMvc.perform(post(ProductServiceUris.ProductCategory.DELETE, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(categoryService, times(1)).deleteBatch(ids);
    }

    @Test
    void testUpdate() throws Exception {
        CategoryUpdateDTO dto = new CategoryUpdateDTO();
        dto.setCatId(1L);
        dto.setName("电子产品(已更新)");
        dto.setIcon("new-icon");
        dto.setSort(5);
        dto.setShowStatus(0);

        doNothing().when(categoryService).update(any(CategoryUpdateDTO.class));

        mockMvc.perform(post(ProductServiceUris.ProductCategory.UPDATE, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(categoryService, times(1)).update(any(CategoryUpdateDTO.class));
    }

    @Test
    void testUpdateValidationFail() throws Exception {
        CategoryUpdateDTO dto = new CategoryUpdateDTO();
        dto.setName("电子产品(已更新)");

        mockMvc.perform(post(ProductServiceUris.ProductCategory.UPDATE, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(categoryService, never()).update(any(CategoryUpdateDTO.class));
    }

    @Test
    void testServiceThrowsException() throws Exception {
        when(categoryService.listWithTree()).thenThrow(new RuntimeException("数据库连接异常"));

        mockMvc.perform(get(ProductServiceUris.ProductCategory.LIST_TREE))
                .andExpect(status().isInternalServerError());

        verify(categoryService, times(1)).listWithTree();
    }
}

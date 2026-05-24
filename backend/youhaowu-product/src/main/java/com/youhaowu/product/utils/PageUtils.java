package com.youhaowu.product.utils;

import cn.hutool.core.util.StrUtil;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youhaowu.common.utils.PageData;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * MyBatis-Plus 分页工具
 */
public class PageUtils {

    /** 只允许字母、数字、下划线的列名，防止 SQL 注入 */
    private static final String COLUMN_PATTERN = "^[a-zA-Z_]\\w*$";

    // ==================== Map 参数兼容 ====================

    private static final String PAGE = "page";
    private static final String LIMIT = "limit";

    /**
     * 从 Map 参数构建 Page 对象（兼容前端直接传 Map 的场景）
     */
    public static <T> Page<T> buildPage(Map<String, Object> params) {
        long curPage = 1;
        long limit = 10;
        if (params.get(PAGE) != null) {
            curPage = Long.parseLong(String.valueOf(params.get(PAGE)));
        }
        if (params.get(LIMIT) != null) {
            limit = Long.parseLong(String.valueOf(params.get(LIMIT)));
        }
        return new Page<>(curPage, limit);
    }

    /**
     * 从 QueryDTO 构建 MP Page 对象
     */
    public static <T> Page<T> buildPage(BaseQueryDTO query) {
        long pageNum = query.getPageNum() != null ? query.getPageNum() : 1L;
        long pageSize = query.getPageSize() != null ? query.getPageSize() : 10L;
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 应用动态排序：仅当 sortField 有值时生效
     */
    public static <T> void applySort(LambdaQueryWrapper<T> wrapper, BaseQueryDTO query) {
        if (StrUtil.isNotBlank(query.getSortField())) {
            String field = query.getSortField().trim();
            if (!field.matches(COLUMN_PATTERN)) {
                throw new IllegalArgumentException("非法的排序字段: " + field);
            }
            String direction = !Boolean.FALSE.equals(query.getSortAsc()) ? " ASC" : " DESC";
            wrapper.last("ORDER BY " + field + direction);
        }
    }

    /**
     * 执行分页查询并转换为 VO 列表
     */
    public static <E, V> PageData<V> selectPage(BaseMapper<E> mapper,
                                                  LambdaQueryWrapper<E> wrapper,
                                                  BaseQueryDTO query,
                                                  Function<E, V> converter) {
        applySort(wrapper, query);
        Page<E> result = mapper.selectPage(buildPage(query), wrapper);
        List<V> voList = result.getRecords().stream().map(converter).collect(Collectors.toList());
        return PageData.of(result.getTotal(), result.getSize(), result.getCurrent(), voList);
    }

    /**
     * 兼容 2022 风格的 IPage 包装（用于简单分页场景）
     */
    public static PageUtils of(IPage<?> page) {
        return new PageUtils(page);
    }

    private final int totalCount;
    private final int pageSize;
    private final int totalPage;
    private final int currPage;
    private final List<?> list;

    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public PageUtils(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int) page.getTotal();
        this.pageSize = (int) page.getSize();
        this.currPage = (int) page.getCurrent();
        this.totalPage = (int) page.getPages();
    }

    public int getTotalCount() { return totalCount; }
    public int getPageSize() { return pageSize; }
    public int getTotalPage() { return totalPage; }
    public int getCurrPage() { return currPage; }
    public List<?> getList() { return list; }
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Class<T> type) { return (List<T>) list; }
}

package com.wheatmall.common.utils;

import com.alibaba.fastjson.TypeReference;
import com.wheatmall.common.enums.BizCodeEnum;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * R类单元测试
 */
class RTest {

   
    @Test
    void testGetData() {
        // 测试setData设置对象，getData获取对象
        R<String> r = R.ok();
        r.setData("test data");
        
        // getData应该返回T类型（String）
        String data = r.getData();
        assertNotNull(data);
        assertEquals("test data", data);
    }

    @Test
    void testSetData() {
        R<String> r = R.ok();
        r.setData("test data");
        assertEquals("test data", r.getData());
    }

    @Test
    void testPut() {
        R<String> r = R.ok();
        r.put("customKey", "customValue");
        assertEquals("customValue", r.get("customKey"));
    }

    @Test
    void testChain() {
        R<String> r = R.<String>ok()
                .put("key1", "value1")
                .put("key2", "value2")
                .setData("data");

        assertEquals(0, r.getCode());
        assertEquals("value1", r.get("key1"));
        assertEquals("value2", r.get("key2"));
        assertEquals("data", r.getData());
    }

    @Test
    void testGetDataWithObject() {
        // 测试getData返回对象类型
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1L);
        data.put("name", "测试");
        
        // 注意：ok(data)如果是Map，是把数据放到extra，不是data字段
        // 应该用ok()空构造，再用setData()设置data字段
        R<Map<String, Object>> r = R.ok();
        r.setData(data);
        
        // getData直接返回原始T类型数据
        Map<String, Object> result = r.getData();
        assertNotNull(result);
        assertEquals(1L, result.get("id"));
        assertEquals("测试", result.get("name"));
    }

    @Test
    void testGetDataWithClass() {
        // 测试使用 Class 获取数据（方式1：简单）
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1L);
        data.put("name", "测试");
        
        R<Map<String, Object>> r = R.ok();
        r.setData(data);
        
        // 使用 Class 获取数据
        @SuppressWarnings("unchecked")
        Map<String, Object> result = r.getData(Map.class);
        assertNotNull(result);
        assertEquals("测试", result.get("name"));
        
        // 也可以用于简单类型
        R<String> r2 = R.ok();
        r2.setData("test string");
        String str = r2.getData(String.class);
        assertEquals("test string", str);
    }

    @Test
    void testGetDataWithTypeReference() {
        R<Map<String, Object>> r = R.ok();

        Map<String, Object> data = new HashMap<>();
        data.put("id", 1L);
        data.put("name", "测试");
        r.setData(data);

        // getData(TypeReference)用于类型转换（fastjson反序列化），支持泛型
        Map<String, Object> result = r.getData(new TypeReference<Map<String, Object>>() {});
        assertNotNull(result);
        // fastjson反序列化会将Long转为Integer，这里用Number类型比较
        assertEquals(1, ((Number) result.get("id")).intValue());
        assertEquals("测试", result.get("name"));
    }

   
}

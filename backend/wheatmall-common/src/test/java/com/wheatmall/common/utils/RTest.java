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
    void testOk() {
        R r = R.ok();
        assertEquals(0, r.getCode());
        assertEquals("success", r.get("msg"));
    }

    @Test
    void testOkWithMessage() {
        R r = R.ok("操作成功");
        assertEquals(0, r.getCode());
        assertEquals("操作成功", r.get("msg"));
    }

    @Test
    void testOkWithMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", 123);

        R r = R.ok(map);
        assertEquals(0, r.getCode());
        assertEquals("value1", r.get("key1"));
        assertEquals(123, r.get("key2"));
    }

    @Test
    void testError() {
        R r = R.error();
        assertEquals(500, r.getCode());
        assertEquals("未知异常，请联系管理员", r.get("msg"));
    }

    @Test
    void testErrorWithMessage() {
        R r = R.error("自定义错误");
        assertEquals(500, r.getCode());
        assertEquals("自定义错误", r.get("msg"));
    }

    @Test
    void testErrorWithBizCode() {
        R r = R.error(BizCodeEnum.PRODUCT_NOT_FOUND);
        assertEquals(20001, r.getCode());
        assertEquals("商品不存在", r.get("msg"));
    }

    @Test
    void testErrorWithCodeAndMessage() {
        R r = R.error(400, "参数错误");
        assertEquals(400, r.getCode());
        assertEquals("参数错误", r.get("msg"));
    }

    @Test
    void testSetData() {
        R r = R.ok();
        r.setData("test data");
        assertEquals("test data", r.get("data"));
    }

    @Test
    void testPut() {
        R r = R.ok();
        r.put("customKey", "customValue");
        assertEquals("customValue", r.get("customKey"));
    }

    @Test
    void testChain() {
        R r = R.ok()
                .put("key1", "value1")
                .put("key2", "value2")
                .setData("data");

        assertEquals(0, r.getCode());
        assertEquals("value1", r.get("key1"));
        assertEquals("value2", r.get("key2"));
        assertEquals("data", r.get("data"));
    }

    @Test
    void testGetDataWithTypeReference() {
        R r = R.ok();

        Map<String, Object> data = new HashMap<>();
        data.put("id", 1L);
        data.put("name", "测试");
        r.setData(data);

        Map<String, Object> result = r.getData(new TypeReference<Map<String, Object>>() {});
        assertNotNull(result);
        // fastjson反序列化会将Long转为Integer，这里用Number类型比较
        assertEquals(1, ((Number) result.get("id")).intValue());
        assertEquals("测试", result.get("name"));
    }
}

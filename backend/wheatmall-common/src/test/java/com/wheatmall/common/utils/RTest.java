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
        R<String> r = R.ok();
        assertEquals(0, r.getCode());
        assertEquals("success", r.getMsg());
    }

    @Test
    void testOkWithMessage() {
        R<String> r = R.ok("操作成功");
        assertEquals(0, r.getCode());
        assertEquals("操作成功", r.getMsg());
    }

    @Test
    void testOkWithMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", 123);

        R<Object> r = R.ok(map);
        assertEquals(0, r.getCode());
        assertNotNull(r.getExtra());
    }

    @Test
    void testFail() {
        R<String> r = R.fail();
        assertEquals(500, r.getCode());
        assertEquals("未知异常，请联系管理员", r.getMsg());
    }

    @Test
    void testFailWithMessage() {
        R<String> r = R.fail("自定义错误");
        assertEquals(500, r.getCode());
        assertEquals("自定义错误", r.getMsg());
    }

    @Test
    void testFailWithBizCode() {
        R<String> r = R.fail(BizCodeEnum.PRODUCT_NOT_FOUND);
        assertEquals(20001, r.getCode());
        assertEquals("商品不存在", r.getMsg());
    }

    @Test
    void testFailWithCodeAndMessage() {
        R<String> r = R.fail(400, "参数错误");
        assertEquals(400, r.getCode());
        assertEquals("参数错误", r.getMsg());
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
    void testGetDataWithTypeReference() {
        R<Map<String, Object>> r = R.ok();

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

    @Test
    void testIsSuccess() {
        R<String> successR = R.ok();
        assertTrue(successR.isSuccess());

        R<String> failR = R.fail();
        assertFalse(failR.isSuccess());
    }

    @Test
    void testSerializable() {
        R<String> r = R.ok("test");
        r.setData("test data");
        
        // 验证实现了Serializable接口
        assertTrue(r instanceof java.io.Serializable);
    }
}

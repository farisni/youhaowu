package com.wheatmall.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wheatmall.common.enums.BizCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结果封装
 * @param <T> 数据泛型
 */
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 响应码：0表示成功，其他表示失败
	 */
	private Integer code;

	/**
	 * 响应消息
	 */
	private String msg;

	/**
	 * 响应数据
	 */
	private T data;

	/**
	 * 扩展字段，用于存放额外信息
	 */
	private Map<String, Object> extra;

	public R() {
		this.code = 0;
		this.msg = "success";
	}

	/**
	 * 设置数据并返回自身（链式调用）
	 */
	public R<T> setData(T data) {
		this.data = data;
		return this;
	}

	/**
	 * 添加扩展字段
	 */
	public R<T> put(String key, Object value) {
		if (this.extra == null) {
			this.extra = new HashMap<>();
		}
		this.extra.put(key, value);
		return this;
	}

	/**
	 * 获取扩展字段值
	 */
	public Object get(String key) {
		if (this.extra == null) {
			return null;
		}
		return this.extra.get(key);
	}

	/**
	 * 利用fastjson进行反序列化，将data转换为指定类型（使用Class）
	 */
	public <E> E getData(Class<E> clazz) {
		String jsonString = JSON.toJSONString(this.data);
		return JSON.parseObject(jsonString, clazz);
	}

	/**
	 * 利用fastjson进行反序列化，将data转换为指定类型（使用TypeReference，支持泛型）
	 */
	public <E> E getData(TypeReference<E> typeReference) {
		String jsonString = JSON.toJSONString(this.data);
		return JSON.parseObject(jsonString, typeReference);
	}

	/**
	 * 判断是否成功
	 */
	public boolean isSuccess() {
		return this.code != null && this.code == 0;
	}

	/**
	 * 创建失败响应
	 */
	public static <T> R<T> fail() {
		return fail(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	/**
	 * 创建失败响应（自定义消息）
	 */
	public static <T> R<T> fail(String msg) {
		return fail(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	/**
	 * 创建失败响应（业务错误码）
	 */
	public static <T> R<T> fail(BizCodeEnum bizCode) {
		return fail(bizCode.getCode(), bizCode.getMessage());
	}

	/**
	 * 创建失败响应（自定义码和消息）
	 */
	public static <T> R<T> fail(int code, String msg) {
		R<T> r = new R<>();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	/**
	 * 创建成功响应（自定义消息）
	 */
	public static <T> R<T> ok(String msg) {
		R<T> r = new R<>();
		r.setMsg(msg);
		return r;
	}

	/**
	 * 创建成功响应（带数据）
	 */
	public static <T> R<T> ok(T data) {
		R<T> r = new R<>();
		r.setData(data);
		return r;
	}

	/**
	 * 创建成功响应（带扩展字段）
	 */
	public static <T> R<T> ok(Map<String, Object> extra) {
		R<T> r = new R<>();
		if (extra != null) {
			extra.forEach(r::put);
		}
		return r;
	}

	/**
	 * 创建成功响应
	 */
	public static <T> R<T> ok() {
		return new R<>();
	}
}

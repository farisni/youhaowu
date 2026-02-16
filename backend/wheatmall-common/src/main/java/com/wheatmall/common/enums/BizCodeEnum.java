package com.wheatmall.common.enums;

import lombok.Getter;

/**
 * 业务错误码枚举
 */
@Getter
public enum BizCodeEnum {

	/**
	 * 系统错误
	 */
	SYSTEM_ERROR(500, "系统错误"),
	
	/**
	 * 参数错误
	 */
	PARAM_ERROR(400, "参数错误"),
	
	/**
	 * 未登录
	 */
	NOT_LOGIN(401, "未登录"),
	
	/**
	 * 无权限
	 */
	NO_PERMISSION(403, "无权限"),
	
	/**
	 * 资源不存在
	 */
	NOT_FOUND(404, "资源不存在"),
	
	/**
	 * 操作失败
	 */
	OPERATION_FAILED(10001, "操作失败"),
	
	/**
	 * 数据已存在
	 */
	DATA_ALREADY_EXISTS(10002, "数据已存在"),
	
	/**
	 * 数据不存在
	 */
	DATA_NOT_FOUND(10003, "数据不存在"),
	
	/**
	 * 库存不足
	 */
	STOCK_NOT_ENOUGH(10004, "库存不足"),
	
	/**
	 * 订单创建失败
	 */
	ORDER_CREATE_FAILED(10005, "订单创建失败"),
	
	/**
	 * 商品不存在
	 */
	PRODUCT_NOT_FOUND(20001, "商品不存在"),
	
	/**
	 * 商品已下架
	 */
	PRODUCT_OFF_SHELF(20002, "商品已下架");

	private final int code;
	private final String message;

	BizCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}

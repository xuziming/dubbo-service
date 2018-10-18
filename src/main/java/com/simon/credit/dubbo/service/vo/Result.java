package com.simon.credit.dubbo.service.vo;

import java.io.Serializable;

import com.simon.credit.dubbo.service.exception.ServiceCode;

/**
 * DUBBO接口通用返回包装对象
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 4610670059554675253L;

	/** 成功调用状态 */
	public static final int RESULT_STATUS_SUCCESS = 200;
	/** 失败调用状态 */
	public static final int RESULT_STATUS_ERROR	  = 500;

	/** 调用状态 */
	private int status = RESULT_STATUS_SUCCESS;

	/** 错误提示信息 */
	private String message;

	/** 返回数据(200成功状态) */
	private T data;

	public Result() {}

	public Result(T data) {
		this.data = data;
	}

	public Result(String message, T data) {
		this.message = message;
		this.data = data;
	}

	public Result(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Result(ServiceCode serviceCode) {
		this.status = serviceCode.getCode();
		this.message = serviceCode.getDesc();
	}

	public Result(ServiceCode serviceCode, T data) {
		this.status = serviceCode.getCode();
		this.message = serviceCode.getDesc();
		this.data = data;
	}

	/**
	 * 调用失败返回结果
	 * @param message 错误提示信息
	 * @return
	 */
	public Result<T> error(String message) {
		this.status = RESULT_STATUS_ERROR;
		this.message = message;
		this.data = null;
		return this;
	}

	/**
	 * 是否成功
	 * @return true:调用成功, false:调用出错
	 */
	public boolean isSuccess() {
		return this.status == RESULT_STATUS_SUCCESS;
	}

	/**
	 * 调用成功返回结果
	 * @param data 操作结果
	 * @return
	 */
	public Result<T> success(T data) {
		this.status = RESULT_STATUS_SUCCESS;
		this.message = null;
		this.data = data;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}

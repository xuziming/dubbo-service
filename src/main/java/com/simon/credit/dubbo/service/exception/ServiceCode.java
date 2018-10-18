package com.simon.credit.dubbo.service.exception;

/**
 * 异常服务编码(勾子)
 * @author XUZIMING 2018-02-01
 */
public interface ServiceCode {

	/** 异常编号 */
	int getCode();

	/** 异常描述说明 */
	String getDesc();

}

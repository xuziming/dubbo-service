package com.simon.credit.dubbo.service.exception;

/**
 * 自定义业务异常
 * @author XUZIMING 2018-02-01
 */
public class BizException extends RuntimeException {
	private static final long serialVersionUID = -3660959256030212664L;

	private ServiceCode errorCode;

	public BizException(ServiceCode errorCode) {
		this.errorCode = errorCode;
	}

	public BizException(ServiceCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BizException(ServiceCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public ServiceCode getErrorCode() {
		return errorCode;
	}

}

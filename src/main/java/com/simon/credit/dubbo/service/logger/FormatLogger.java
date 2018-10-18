package com.simon.credit.dubbo.service.logger;

import org.slf4j.Logger;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Format Logger(delegation pattern)
 * @author XUZIMING 2018-08-15
 */
public final class FormatLogger extends DelegateLogger {

	/** 默认日期格式 */
	private static final String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public FormatLogger(Logger delegator) {
		super(delegator);
	}

	/****************************************************************************************************/
	// LOGGER.debug(...)
	/****************************************************************************************************/

	@Override
	public void debug(String messagePattern, Object arg) {
		delegator.debug(messagePattern, format(arg));

		if (!delegator.isDebugEnabled()) {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void debug(String messagePattern, Object arg1, Object arg2) {
		delegator.debug(messagePattern, format(arg1), format(arg2));

		if (!delegator.isDebugEnabled()) {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void debug(String messagePattern, Object[] args) {
		delegator.debug(messagePattern, format(args));

		if (!delegator.isDebugEnabled()) {
			consolePrint(messagePattern, format(args));
		}
	}

	/****************************************************************************************************/
	// LOGGER.info(...)
	/****************************************************************************************************/

	@Override
	public void info(String messagePattern, Object arg) {
		delegator.info(messagePattern, format(arg));

		if (!delegator.isInfoEnabled()) {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void info(String messagePattern, Object arg1, Object arg2) {
		delegator.info(messagePattern, format(arg1), format(arg2));

		if (!delegator.isInfoEnabled()) {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void info(String messagePattern, Object[] args) {
		delegator.info(messagePattern, format(args));

		if (!delegator.isInfoEnabled()) {
			consolePrint(messagePattern, format(args));
		}
	}

	/****************************************************************************************************/
	// LOGGER.error(...)
	/****************************************************************************************************/

	@Override
	public void error(String messagePattern, Object arg) {
		delegator.error(messagePattern, format(arg));

		if (!delegator.isErrorEnabled()) {
			consolePrint(messagePattern, format(arg));
		}
	}

	@Override
	public void error(String messagePattern, Object arg1, Object arg2) {
		delegator.error(messagePattern, format(arg1), format(arg2));

		if (!delegator.isErrorEnabled()) {
			consolePrint(messagePattern, format(arg1), format(arg2));
		}
	}

	public void error(String messagePattern, Object[] args) {
		delegator.error(messagePattern, format(args));

		if (!delegator.isErrorEnabled()) {
			consolePrint(messagePattern, format(args));
		}
	}

	protected static <T> String format(T object) {
		return JSON.toJSONStringWithDateFormat(object, DEFFAULT_DATE_FORMAT, SerializerFeature.WriteMapNullValue);
	}

	protected static Object[] format(Object[] args) {
		Object[] targets = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			targets[i] = format(args[i]);
		}
		return targets;
	}

	/**
	 * print message to console
	 * @param messagePattern
	 * @param args
	 */
	private void consolePrint(String messagePattern, Object... args) {
		if (messagePattern == null || StringUtils.isEmpty(messagePattern.trim())) {
			return;
		}
		System.out.println(Slf4jFormatter.format(messagePattern, args));
	}

}

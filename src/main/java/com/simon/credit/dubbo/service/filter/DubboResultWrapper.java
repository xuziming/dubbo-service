package com.simon.credit.dubbo.service.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;
import com.simon.credit.dubbo.service.exception.BizException;

/**
 * DUBBO Provider过滤器： 
 * 1 拦截异常的调用, 转化成状态码返回给consumer
 * 2 打印异常日志
 * 3 打印调用响应时间
 */
@Activate(group = { Constants.PROVIDER })
public class DubboResultWrapper implements Filter {

	private static final Logger logger = LoggerFactory.getLogger("com.simon.perf");
	private static final Logger loggerOps = LoggerFactory.getLogger("com.simon.ops");

	@SuppressWarnings("deprecation")
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String requestId = RpcContext.getContext().getAttachment("requestId");
		String loginUser = RpcContext.getContext().getAttachment("userName");
		TraceValue.setCall(new TraceValue.Value(requestId, loginUser));

		long start = System.currentTimeMillis();
		Result result = invoker.invoke(invocation);
		if (result.getResult() == null) {// exception occur
			com.simon.credit.dubbo.service.vo.Result<?> myResult = new com.simon.credit.dubbo.service.vo.Result<>();
			boolean isBizException = false;

			Throwable e = result.getException();
			Throwable eCause = e;
			while (eCause != null) {
				if (eCause instanceof BizException) {
					BizException bizExp = (BizException) eCause;
					myResult.setStatus(bizExp.getErrorCode().getCode());
					myResult.setMessage(bizExp.getErrorCode().getDesc() + ":" + eCause.getMessage());
					isBizException = true;

					break;
				}
				eCause = eCause.getCause();
			}

			// 如果没有抛BizException 默认异常编码为 ErrorCode.SYSTEM_EXCEPTION
			if (!isBizException) {
				myResult.setMessage("系统繁忙, 请稍后再试!");
				myResult.setStatus(com.simon.credit.dubbo.service.vo.Result.RESULT_STATUS_ERROR);
			}

			RpcResult rpcResult = new RpcResult();
			rpcResult.setException(null);
			rpcResult.setResult(myResult);

			logger.error(e.getMessage(), e);

			long elapsed = System.currentTimeMillis() - start;
			String method = invoker.getInterface() + "." + invocation.getMethodName();
			String log = formatLog(requestId, loginUser, method, elapsed, TraceValue.getValue().getCallDepth());
			logger.info(log);

			// 打印ops监控异常日志
			String opsLog = getOpsLogPatternInput(invoker.getInterface().toString(), invocation.getMethodName(), elapsed, 1);
			loggerOps.info(opsLog);

			return rpcResult;

		}

		long elapsed = System.currentTimeMillis() - start;
		String method = invoker.getInterface() + "." + invocation.getMethodName();
		String log = formatLog(requestId, loginUser, method, elapsed, TraceValue.getValue().getCallDepth());
		logger.info(log);

		// 打印ops监控正常日志
		String opsLog = getOpsLogPatternInput(invoker.getInterface().toString(), invocation.getMethodName(), elapsed, 0);
		loggerOps.info(opsLog);

		return result;
	}

	private String formatLog(String requestId, String userName, String method, long elapsed, int callDepth) {
		return new StringBuilder()
				.append("requestId=")
				.append(requestId)
				.append("|")
				.append(userName)
				.append("|")
				.append(method)
				.append("|takes ms:")
				.append(elapsed)
				.append("|call depth:")
				.append(callDepth)
				.toString();
	}

	/**
	 * 获取Ops日志格式输入
	 * @param passiveInvokeService 被调服务名
	 * @param passiveInvokeInterface 被调接口名
	 * @param invokeTime 调用时间
	 * @param invokeResult 调用结果 0：成功 1：失败
	 * @return
	 * @author:zhangxy
	 * @time:2017年3月14日
	 */
	private String getOpsLogPatternInput(String passiveInvokeService,
		String passiveInvokeInterface, long invokeTime, int invokeResult) {

		TraceValue.Value value = TraceValue.getValue();
		return new StringBuilder()
			.append(System.currentTimeMillis() / 1000)
			.append("`0`")
			.append(value.getRequestId())
			.append("`0`")
			.append("")
			.append("`")
			.append(RpcContext.getContext().getRemoteHost())
			.append("`")
			.append(passiveInvokeService)
			.append("`")
			.append(passiveInvokeInterface)
			.append("`")
			.append(RpcContext.getContext().getLocalHost())
			.append("`")
			.append(RpcContext.getContext().getLocalPort())
			.append("`")
			.append(invokeResult).append("`")
			.append(invokeTime)
			.append("```")
			.append(value.getUserName())
			.toString();
	}

}

package com.simon.credit.dubbo.service.filter;

/**
 * 调用链跟踪对象
 */
public class TraceValue {

	private static final ThreadLocal<Value> traceHolder = new ThreadLocal<Value>();

	public static Value getValue() {
		return traceHolder.get();
	}

	public static void setCall(Value trace) {
		traceHolder.set(trace);
	}

	public static void increaseCallDepth() {
		Value value = traceHolder.get();
		if (value != null) {
			value.increaseDepth();
		}
	}

	/** 退出线程，清理数据 */
	public static void clear() {
		traceHolder.remove();
	}

	public static class Value {
		private String requestId;
		private String userName;

		/** 调用IP */
		private String invokeIp;

		/** 本地IP */
		private String localAddr;

		/** 本地端口 */
		private Integer localPort;

		/** 在一个请求里面调用service和dao的深度 */
		private int callDepth;

		public Value(String requestId, String userName) {
			this.requestId = requestId;
			this.userName = userName;
			this.callDepth = 0;
		}

		public Value(String requestId, String userName, String invokeIp, String localAddr, Integer localPort) {
			super();
			this.requestId = requestId;
			this.userName = userName;
			this.invokeIp = invokeIp;
			this.localAddr = localAddr;
			this.localPort = localPort;
			this.callDepth = 0;
		}

		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public void increaseDepth() {
			callDepth++;
		}

		public int getCallDepth() {
			return callDepth;
		}

		public String getInvokeIp() {
			return invokeIp;
		}

		public void setInvokeIp(String invokeIp) {
			this.invokeIp = invokeIp;
		}

		public String getLocalAddr() {
			return localAddr;
		}

		public void setLocalAddr(String localAddr) {
			this.localAddr = localAddr;
		}

		public Integer getLocalPort() {
			return localPort;
		}

		public void setLocalPort(Integer localPort) {
			this.localPort = localPort;
		}

		public void setCallDepth(int callDepth) {
			this.callDepth = callDepth;
		}
	}

}

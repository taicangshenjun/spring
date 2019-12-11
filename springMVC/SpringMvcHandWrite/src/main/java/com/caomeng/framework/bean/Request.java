package com.caomeng.framework.bean;

/**
 * 请求类中的方法和路径对应 @RequestMapping 注解里的方法和路径.
 * @author cm
 *
 */
public class Request {

	/**
	 * 请求方法
	 */
	private String requestMethod;
	
	/**
	 * 请求路径
	 */
	private String requestPath;
	
	public Request(String requestMethod, String requestPath) {
		this.requestMethod = requestMethod;
		this.requestPath = requestPath;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}
	
	public String getRequestPath() {
		return requestPath;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result += result * 31 + getRequestMethod().hashCode();
		result += result * 31 + getRequestPath().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Request))
			return false;
		Request request = (Request) obj;
		if(request.getRequestMethod().equals(this.requestMethod) && request.getRequestPath().equals(this.requestPath)) {
			return true;
		}
		return false;
	}
	
}

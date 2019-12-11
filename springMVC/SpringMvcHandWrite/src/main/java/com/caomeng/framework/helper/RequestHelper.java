package com.caomeng.framework.helper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.caomeng.framework.bean.Param;

/**
 * 前端控制器接收到HTTP请求后, 从HTTP中获取请求参数, 然后封装到Param对象中.
 * @author cm
 *
 */
public final class RequestHelper {

	public static Param createParam(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		if(!paramNames.hasMoreElements()) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//get,post均能获取
		while(paramNames.hasMoreElements()) {
			String fieldName = paramNames.nextElement();
			String fieldValue = request.getParameter(fieldName);
			paramMap.put(fieldName, fieldValue);
		}
		return new Param(paramMap);
	}
	
}

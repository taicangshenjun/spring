package com.spring.custom.argumentResolver;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomService;

/**
 * 处理Request请求参数
 * @author cm
 *
 */
@CustomService("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {

	public boolean support(Class<?> type, int paramIndex, Method method) {
		return ServletRequest.class.isAssignableFrom(type);
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type,
			int paramIndex, Method method) {
		return request;
	}

}

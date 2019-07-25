package com.spring.custom.argumentResolver;

import java.lang.reflect.Method;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomService;

/**
 * 处理Response参数
 * @author cm
 *
 */
@CustomService("httpServletResponseArgumentResolver")
public class HttpServletResponseArgumentResolver implements ArgumentResolver {

	public boolean support(Class<?> type, int paramIndex, Method method) {
		return ServletResponse.class.isAssignableFrom(type);
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type,
			int paramIndex, Method method) {
		return response;
	}

}

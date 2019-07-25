package com.spring.custom.argumentResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomRequestParam;

/**
 * 处理自定义的参数
 * @author cm
 *
 */
public class RequestParamArgumentResolver implements ArgumentResolver {

	public boolean support(Class<?> type, int paramIndex, Method method) {
		// type = class java.lang.String
		// @CustomRequestParam("name")String name
		//获取当前方法的参数
		Annotation[][] an = method.getParameterAnnotations();
		Annotation[] paramAns = an[paramIndex];
		
		for (Annotation paramAn : paramAns) {
        	//判断传进的paramAn.getClass()是不是 CustomRequestParam 类型
        	if (CustomRequestParam.class.isAssignableFrom(paramAn.getClass())) {
                return true;
            }
		}
		return false;
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type,
			int paramIndex, Method method) {
		Annotation[][] an = method.getParameterAnnotations();
		Annotation[] paramAns = an[paramIndex];
		
		for(Annotation paramAn: paramAns) {
			//判断传进的paramAn.getClass()是不是 CustomRequestParam 类型
			if(CustomRequestParam.class.isAssignableFrom(paramAn.getClass())) {
				CustomRequestParam cr = (CustomRequestParam) paramAn;
				String value = cr.value();
				return request.getParameter(value);
			}
			
		}
		return null;
	}

}

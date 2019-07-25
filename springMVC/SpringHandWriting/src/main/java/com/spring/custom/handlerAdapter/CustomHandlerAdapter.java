package com.spring.custom.handlerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomService;
import com.spring.custom.argumentResolver.ArgumentResolver;

@CustomService("customHandlerAdapter")
public class CustomHandlerAdapter implements HandlerAdapterService {

	public Object[] handle(HttpServletRequest req, HttpServletResponse resp, Method method, Map<String, Object> beans) {
		//��ȡ�����к���Ĳ���
		Class<?>[] paramClazzs = method.getParameterTypes();
		System.out.println("======��ǰ��Ҫ�����Ĳ�����Ӧ����=========");
		for(Class<?> clazz: paramClazzs) {
			System.out.println(clazz);
		}
		
		// ����һ�����ز����Ľ����
		Object[] args = new Object[paramClazzs.length];
		
		// ����һ��ArgumentResolverʵ�����Map
		Map<String, Object> argumentResolvers = getBeansOfType(beans, ArgumentResolver.class);
		System.out.println("======��ǰ��Ҫ�����Ĳ�����Ӧ����ʵ����======");
		for(Map.Entry<String, Object> map: argumentResolvers.entrySet()) {
			System.out.println("key:" + map.getKey() + "; value:" + map.getValue());
		}
		
		//�����������
		int paramIndex = 0;
		//���������±�����
		int i =0;
		// ��ʼ�������
		for(Class<?> paramClazz: paramClazzs) {
			for(Map.Entry<String, Object> entry: argumentResolvers.entrySet()) {
				ArgumentResolver ar = (ArgumentResolver) entry.getValue();
				if(ar.support(paramClazz, paramIndex, method)) {
					args[i ++] = ar.argumentResolver(req, resp, paramClazz, paramIndex, method);
				}
			}
			paramIndex ++;
		}
		return args;
	}
	
	private Map<String, Object> getBeansOfType(Map<String, Object> beans, Class<ArgumentResolver> intfType){
		Map<String, Object> resultBeans = new HashMap<String, Object>();
		for(Map.Entry<String, Object> map: beans.entrySet()) {
			// ��ȡ����ArgumentResolver�ӿڵ�bean
			Class<?>[] intfs = map.getValue().getClass().getInterfaces();
			if(intfs != null && intfs.length > 0) {
				for(Class<?> intf: intfs) {
					// �������bean�洢��resultBeans��
					if(intf.isAssignableFrom(intfType))
						resultBeans.put(map.getKey(), map.getValue());
				}
			}
		}
		
		
		
		
		
		
		
		return null;
	}

}

package com.caomeng.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.caomeng.framework.annotation.RequestMapping;
import com.caomeng.framework.bean.Handler;
import com.caomeng.framework.bean.Request;

/**
 * ControllerHelper 助手类定义了一个"请求-处理器" 的映射 REQUEST_MAP, 
 * REQUEST_MAP 就相当于Spring MVC里的映射处理器, 
 * 接收到请求后返回对应的处理器.REQUEST_MAP 映射处理器的实现逻辑如下:
 * 首先通过 ClassHelper 工具类获取到应用中所有Controller的Class对象, 
 * 然后遍历Controller及其所有方法, 将所有带 @RequestMapping 注解的方法封装为处理器, 
 * 将 @RequestMapping 注解里的请求路径和请求方法封装成请求对象, 然后存入 REQUEST_MAP 中.
 * @author cm
 *
 */
public final class ControllerHelper {

	/**
	 * REQUEST_MAP为 "请求-处理器" 的映射
	 */
	private static final Map<Request, Handler> REQUEST_MAP = new HashMap<Request, Handler>();
	
	static {
		Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
		if(CollectionUtils.isNotEmpty(controllerSet)) {
			for(Class<?> controller: controllerSet) {
				//获取所有公共方法
				Method[] methods = controller.getMethods();
				if(ArrayUtils.isNotEmpty(methods)) {
					for(Method method: methods) {
						if(method.isAnnotationPresent(RequestMapping.class)) {
							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							 //请求方法
                            String requestMethod = requestMapping.method().name();
							//请求路径
                            String requestPath = requestMapping.value();

                            //封装请求和处理器
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controller, method);
                            REQUEST_MAP.put(request, handler);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取Handler
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return REQUEST_MAP.get(request);
	}
	
}

package com.caomeng.framework.bean;

import java.lang.reflect.Method;

/**
 * Handler类为一个处理器, 封装了Controller的Class对象和Method方法.
 * @author cm
 *
 */
public class Handler {

	/**
	 * Controller类
	 */
	private Class<?> controllerClass;
	
	/**
	 * Controller方法
	 */
	private Method controllerMethod;
	
	public Handler(Class<?> controllerClass, Method controllerMethod) {
		this.controllerClass = controllerClass;
		this.controllerMethod = controllerMethod;
	}
	
	public Class<?> getControllerClass(){
		return this.controllerClass;
	}
	
	public Method getControllerMethod() {
		return this.controllerMethod;
	}
	
}

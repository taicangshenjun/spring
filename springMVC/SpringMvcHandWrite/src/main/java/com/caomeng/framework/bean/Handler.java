package com.caomeng.framework.bean;

import java.lang.reflect.Method;

/**
 * Handler��Ϊһ��������, ��װ��Controller��Class�����Method����.
 * @author cm
 *
 */
public class Handler {

	/**
	 * Controller��
	 */
	private Class<?> controllerClass;
	
	/**
	 * Controller����
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

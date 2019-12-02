package com.caomeng.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.caomeng.framework.annotation.Controller;
import com.caomeng.framework.annotation.Service;
import com.caomeng.framework.util.ClassUtil;

public final class ClassHelper {

	/**
	 * 定义类集合（存放基础包下所有类）
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static {
		//获取基础包名
		String basePackage = ConfigHelper.getAppBasePackage();
		//获取基础包名下的所有类
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取基础包名下的所有类
	 * @return
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取基础包名下所有Service类
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls: CLASS_SET) {
			if(cls.isAnnotationPresent(Service.class))
				classSet.add(cls);
		}
		return classSet;
	}
	
	/**
	 * 获取基础包名下所有Controller类
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls: CLASS_SET) {
			if(cls.isAnnotationPresent(Controller.class))
				classSet.add(cls);
		}
		return classSet;
	}
	
	/**
	 * 获取基础包名下所有Bean(包括Controller,Service)类
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
	
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls: CLASS_SET) {
			//isAssignableFrom()指superClass和cls是否相同或superClass是否是cls的父类/接口
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls))
				classSet.add(cls);
		}
		return classSet;
	}
	
	/**
	 * 获取基础包名下带有某注解的所有类
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls: CLASS_SET) {
			if(cls.isAnnotationPresent(annotationClass))
				classSet.add(cls);
		}
		return classSet;
	}
	
}

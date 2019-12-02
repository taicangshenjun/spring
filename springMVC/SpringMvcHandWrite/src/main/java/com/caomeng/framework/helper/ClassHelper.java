package com.caomeng.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.caomeng.framework.annotation.Controller;
import com.caomeng.framework.annotation.Service;
import com.caomeng.framework.util.ClassUtil;

public final class ClassHelper {

	/**
	 * �����༯�ϣ���Ż������������ࣩ
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static {
		//��ȡ��������
		String basePackage = ConfigHelper.getAppBasePackage();
		//��ȡ���������µ�������
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * ��ȡ���������µ�������
	 * @return
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * ��ȡ��������������Service��
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
	 * ��ȡ��������������Controller��
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
	 * ��ȡ��������������Bean(����Controller,Service)��
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
			//isAssignableFrom()ָsuperClass��cls�Ƿ���ͬ��superClass�Ƿ���cls�ĸ���/�ӿ�
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls))
				classSet.add(cls);
		}
		return classSet;
	}
	
	/**
	 * ��ȡ���������´���ĳע���������
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

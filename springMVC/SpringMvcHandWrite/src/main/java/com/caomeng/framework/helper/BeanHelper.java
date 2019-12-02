package com.caomeng.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.caomeng.framework.util.ReflectionUtil;

public final class BeanHelper {

	/**
	 * �൱��spring������ӵ��Ӧ�����е�beanʵ��
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();
	
	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for(Class<?> beanClass: beanClassSet) {
			Object obj = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}
	
	/**
     * ��ȡBean����
     */
	public static Map<Class<?>, Object> getBeanMap(){
		return BEAN_MAP;
	}
	
	/**
     * ��ȡBeanʵ��
     */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
		if(!BEAN_MAP.containsKey(cls))
			throw new RuntimeException("can not get bean by class��" + cls);
		return (T) BEAN_MAP.get(cls);
	}
	
	/**
     * ����Beanʵ��
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
	
}

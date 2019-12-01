package com.caomeng.frameworkcom.caomeng.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.caomeng.frameworkcom.caomeng.framework.annotation.Autowired;
import com.caomeng.frameworkcom.caomeng.framework.util.ReflectionUtil;

public final class IOCHelper {

	/**
	 * ����bean��������bean������, Ϊ���д�@Autowiredע�������ע��ʵ��
	 */
	static {
		//����bean�����������bean
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if(MapUtils.isNotEmpty(beanMap)) {
			for(Map.Entry<Class<?>, Object> beanEntry: beanMap.entrySet()) {
				//bean��class��
				Class<?> beanClass = beanEntry.getKey();
				//bean��ʵ��
				Object beanInstance = beanEntry.getValue();
				//���������ȡ����
				Field[] beanFields = beanClass.getDeclaredFields();
				//����bean������
				if(ArrayUtils.isNotEmpty(beanFields)) {
					for(Field beanField: beanFields) {
						//�ж������Ƿ��Autowiredע��
						if(beanField.isAnnotationPresent(Autowired.class)) {
							//��ȥ�ö������������
							Class<?> beanFieldClass = beanField.getType();
							//���beanFieldClass�ǽӿ�, �ͻ�ȡ�ӿڶ�Ӧ��ʵ����
							beanFieldClass = findImplementClass(beanFieldClass);
							//��ȡClass���Ӧ��ʵ��
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if(beanFieldInstance != null)
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
						}
					}
				}
			}
		}
	}
	
	/**
     * ��ȡ�ӿڶ�Ӧ��ʵ����
     */
	public static Class<?> findImplementClass(Class<?> interfaceClass){
		Class<?> implementClass = interfaceClass;
		//�ӿڶ�Ӧ������ʵ����
		Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(interfaceClass);
		if(CollectionUtils.isNotEmpty(classSetBySuper))
			//��ȡ��һ��ʵ����
			implementClass = classSetBySuper.iterator().next();
		return implementClass;
	}
	
}

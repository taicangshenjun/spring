package com.caomeng.framework.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caomeng.framework.annotation.Aspect;
import com.caomeng.framework.proxy.AspectProxy;
import com.caomeng.framework.proxy.Proxy;
import com.caomeng.framework.proxy.ProxyFactory;
import com.caomeng.framework.util.ClassUtil;

/**
 * AopHelper ������������ʼ������AOP���
 * ���������Bean��ʵ�����Ǵ�Bean�����л�ȡ, Ȼ����ִ�и�ʵ���ķ���, 
 * ���ڴ�, ��ʼ��AOP���ʵ���Ͼ����ô�����󸲸ǵ�Bean�����е�Ŀ�����, 
 * ��������Ŀ�����Class�����Bean�����л�ȡ���ľ��Ǵ������, �Ӷ��ﵽ�˶�Ŀ�������ǿ��Ŀ��.
 * @author cm
 *
 */
public final class AopHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
	
	static {
		try {
			//������-Ŀ���༯�ϵ�ӳ��
			Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
			//Ŀ����-��������б��ӳ��
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
			//���������֯�뵽Ŀ������, �����������
			for(Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
                //����Bean������Ŀ�����Ӧ��ʵ��, �´δ�Bean������ȡ�ľ��Ǵ��������
                BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			LOGGER.error("aop failure", e);
		}
	}

	private static Map<Class<?>, Set<Class<?>>> createAspectMap() throws Exception{
		Map<Class<?>, Set<Class<?>>> aspectMap = new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(aspectMap);
		return aspectMap;
	}
	
	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
		Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> aspectClass: aspectClassSet) {
			if(aspectClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = aspectClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				aspectMap.put(aspectClass, targetClassSet);
			}
		}
	}
	
	/**
	 * ����@Aspect����İ���������ȥ��ȡ��Ӧ��Ŀ���༯��
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		// ����
		String pkg = aspect.pkg();
		// ����
		String cls = aspect.cls();
		// �����������������Ϊ�գ������ָ����
		if(!"".equals(pkg) && !"".equals(cls)) {
			targetClassSet.add(Class.forName(pkg + "." + cls));
		}else {
			// ���������Ϊ��, ����Ϊ��, ����Ӹð�����������
			targetClassSet.addAll(ClassUtil.getClassSet(pkg));
		}
		return targetClassSet;
	}
	
	/**
	 * ��������-Ŀ���༯�ϵ�ӳ���ϵ ת��Ϊ Ŀ����-��������б��ӳ���ϵ
	 * @param aspectMap
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception{
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for(Entry<Class<?>, Set<Class<?>>> proxyEntry: aspectMap.entrySet()) {
			//������
			Class<?> aspectClass = proxyEntry.getKey();
			//Ŀ���༯��
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			//����Ŀ����-�������б��ӳ���ϵ
			for(Class<?> targetClass: targetClassSet) {
				//�������
				Proxy aspect = (Proxy) aspectClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(aspect);
                } else {
                    //��������б�
                    List<Proxy> aspectList = new ArrayList<Proxy>();
                    aspectList.add(aspect);
                    targetMap.put(targetClass, aspectList);
                }
			}
		}
		return targetMap;
	}
	
	
	
	
	
	
	
	
	
}

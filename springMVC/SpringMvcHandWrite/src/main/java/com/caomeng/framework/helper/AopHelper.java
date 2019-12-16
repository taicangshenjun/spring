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
 * AopHelper 助手类用来初始化整个AOP框架
 * 框架中所有Bean的实例都是从Bean容器中获取, 然后再执行该实例的方法, 
 * 基于此, 初始化AOP框架实际上就是用代理对象覆盖掉Bean容器中的目标对象, 
 * 这样根据目标类的Class对象从Bean容器中获取到的就是代理对象, 从而达到了对目标对象增强的目的.
 * @author cm
 *
 */
public final class AopHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
	
	static {
		try {
			//切面类-目标类集合的映射
			Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
			//目标类-切面对象列表的映射
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
			//把切面对象织入到目标类中, 创建代理对象
			for(Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
                //覆盖Bean容器里目标类对应的实例, 下次从Bean容器获取的就是代理对象了
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
	 * 根据@Aspect定义的包名和类名去获取对应的目标类集合
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		// 包名
		String pkg = aspect.pkg();
		// 类名
		String cls = aspect.cls();
		// 如果包名与类名均不为空，则添加指定类
		if(!"".equals(pkg) && !"".equals(cls)) {
			targetClassSet.add(Class.forName(pkg + "." + cls));
		}else {
			// 如果包名不为空, 类名为空, 则添加该包名下所有类
			targetClassSet.addAll(ClassUtil.getClassSet(pkg));
		}
		return targetClassSet;
	}
	
	/**
	 * 将切面类-目标类集合的映射关系 转化为 目标类-切面对象列表的映射关系
	 * @param aspectMap
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception{
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for(Entry<Class<?>, Set<Class<?>>> proxyEntry: aspectMap.entrySet()) {
			//切面类
			Class<?> aspectClass = proxyEntry.getKey();
			//目标类集合
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			//创建目标类-切面类列表的映射关系
			for(Class<?> targetClass: targetClassSet) {
				//切面对象
				Proxy aspect = (Proxy) aspectClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(aspect);
                } else {
                    //切面对象列表
                    List<Proxy> aspectList = new ArrayList<Proxy>();
                    aspectList.add(aspect);
                    targetMap.put(targetClass, aspectList);
                }
			}
		}
		return targetMap;
	}
	
	
	
	
	
	
	
	
	
}

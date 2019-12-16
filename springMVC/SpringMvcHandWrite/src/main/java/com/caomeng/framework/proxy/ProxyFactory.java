package com.caomeng.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory {

	/**
	 * ����һ��Ŀ�����һ��Proxy�ӿ�ʵ��, ���һ���������
	 * @param <T>
	 * @param targetClass
	 * @param proxyList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			
			/**
			 * ������, ÿ�ε���Ŀ�귽��ʱ�����ȴ���һ�� ProxyChain ����, Ȼ����øö���� doProxyChain() ����.
			 */
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				return new ProxyChain(targetClass, obj, method, proxy, args, proxyList);
			}
			
		});
	}
	
}

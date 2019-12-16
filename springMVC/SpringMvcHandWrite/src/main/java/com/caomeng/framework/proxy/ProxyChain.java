package com.caomeng.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * ��������, proxyList �洢���Ǵ����б�(Ҳ������ǿ�б�), 
 * ��ִ��doProxyChain() ����ʱ�ᰴ��˳��ִ����ǿ, �����ִ��Ŀ�귽��.
 * @author cm
 *
 */
public class ProxyChain {
	
	//Ŀ����
	private final Class<?> targetClass;
	
	//Ŀ�����
	private final Object targetObject;
	
	//Ŀ�귽��
	private final Method targetMethod;
	
	//��������
	private final MethodProxy methodProxy;
	
	//��������
	private final Object[] methodParams;
	
	//�����б�
	private List<Proxy> proxyList = new ArrayList<Proxy>();
	
	//��������
	private int proxyIndex = 0;
	
	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, 
			MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}
	
	public Object[] getMethodParams() {
		return this.methodParams;
	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	
	public Method getTargetMethod() {
		return targetMethod;
	}
	
	/**
     * �ݹ�ִ��
     */
	public Object doProxyChain() throws Throwable {
		Object methodResult = null;
		if(proxyIndex < proxyList.size())
			//ִ����ǿ����
			methodResult = proxyList.get(proxyIndex ++).doProxy(this);
		else
			//Ŀ�귽�����ִ����ִֻ��һ��
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		return methodResult;
	}
	
	
	
	
	
	
	
	
	

}

package com.caomeng.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AspectProxy��һ�����������, ʵ����Proxy�ӿ�, ���ж�����������жϺ͸�����ǿ. 
 * ��ִ�� doProxy() ����ʱ, ���Ƚ���������ж�, ��ִ��ǰ����ǿ, ����������һ��doProxyChain()����, ������ǿ��.
 * @author cm
 *
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try {
			if(intercept(method, params)) {
				before(method, params);
				result = proxyChain.doProxyChain();
				after(method, params);
			}else {
				result = proxyChain.doProxyChain();
			}
		}catch(Exception e) {
			logger.error("proxy failure", e);
			error(method, params, e);
			throw e;
		}finally {
			end();
		}
		return result;
	}
	
	/**
	 * ��ʼ��ǿ
	 */
	public void begin() {
		
	}
	
	/**
	 * ������ж�
	 * @param method
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public boolean intercept(Method method, Object[] params) throws Throwable{
		return true;
	}
	
	/**
     * ǰ����ǿ
     */
    public void before(Method method, Object[] params) throws Throwable {
    	
    }

    /**
     * ������ǿ
     */
    public void after(Method method, Object[] params) throws Throwable {
    	
    }

    /**
     * �쳣��ǿ
     */
    public void error(Method method, Object[] params, Throwable e) {
    	
    }

    /**
     * ������ǿ
     */
    public void end() {
    	
    }
	
}

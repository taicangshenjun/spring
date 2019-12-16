package com.caomeng.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AspectProxy是一个切面抽象类, 实现了Proxy接口, 类中定义了切入点判断和各种增强. 
 * 当执行 doProxy() 方法时, 会先进行切入点判断, 再执行前置增强, 代理链的下一个doProxyChain()方法, 后置增强等.
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
	 * 开始增强
	 */
	public void begin() {
		
	}
	
	/**
	 * 切入点判断
	 * @param method
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public boolean intercept(Method method, Object[] params) throws Throwable{
		return true;
	}
	
	/**
     * 前置增强
     */
    public void before(Method method, Object[] params) throws Throwable {
    	
    }

    /**
     * 后置增强
     */
    public void after(Method method, Object[] params) throws Throwable {
    	
    }

    /**
     * 异常增强
     */
    public void error(Method method, Object[] params, Throwable e) {
    	
    }

    /**
     * 最终增强
     */
    public void end() {
    	
    }
	
}

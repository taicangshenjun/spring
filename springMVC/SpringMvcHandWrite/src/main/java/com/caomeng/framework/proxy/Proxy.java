package com.caomeng.framework.proxy;

/**
 * 最上层的代理接口, 其中doProxy()执行的是链式代理
 * @author cm
 *
 */
public interface Proxy {

	/**
     * 执行链式代理
     * 所谓链式代理, 就是说, 可将多个代理通过一条链子串起来, 一个个地去执行, 执行顺序取决于添加到链上的先后顺序
     */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
	
}

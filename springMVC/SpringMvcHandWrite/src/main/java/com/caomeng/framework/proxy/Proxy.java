package com.caomeng.framework.proxy;

/**
 * ���ϲ�Ĵ���ӿ�, ����doProxy()ִ�е�����ʽ����
 * @author cm
 *
 */
public interface Proxy {

	/**
     * ִ����ʽ����
     * ��ν��ʽ����, ����˵, �ɽ��������ͨ��һ�����Ӵ�����, һ������ȥִ��, ִ��˳��ȡ������ӵ����ϵ��Ⱥ�˳��
     */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
	
}

package com.caomeng.framework.bean;

/**
 * Data�����ڷ�װController������JSON���ؽ��.
 * @author cm
 *
 */
public class Data {
	
	private Object model;
	
	public Data(Object model) {
		this.model = model;
	}
	
	public Object getData() {
		return model;
	}

}

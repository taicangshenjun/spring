package com.caomeng.framework.bean;

/**
 * Data类用于封装Controller方法的JSON返回结果.
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

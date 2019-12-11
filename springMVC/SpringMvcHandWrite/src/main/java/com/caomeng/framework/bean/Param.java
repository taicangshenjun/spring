package com.caomeng.framework.bean;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * Param类用于封装Controller方法的参数.
 * @author cm
 *
 */
public class Param {

	private Map<String, Object> paramMap;
	
	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	public Map<String, Object> getParamMap(){
		return paramMap;
	}
	
	public boolean isEmpty() {
		return MapUtils.isEmpty(paramMap);
	}
	
}

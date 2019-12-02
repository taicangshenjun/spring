package com.caomeng.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropsUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
	
	/**
	 * 加载属性文件
	 * @param fileName
	 * @return
	 */
	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = ClassUtil.getClassLoader().getResourceAsStream(fileName);
		}catch(Exception e) {
			LOGGER.error("load properties file failure", e);
		}finally {
			if(is != null) {
				try {
					is.close();
				}catch(Exception e){
					LOGGER.error("close input stream failure", e);
				}
			}
		}
		return props;
	}
	
	/**
     * 获取String类型的属性值（默认值为空字符串）
     */
	public static String getString(Properties props, String key) {
		return getString(props, key, "");
	}
	
	/**
     * 获取String类型的属性值（可指定默认值）
     */
	public static String getString(Properties props, String key, String defaultValue) {
		String value = defaultValue;
		if(props.containsKey(key))
			value = props.getProperty(key);
		return value;
	}
	
	/**
     * 获取int类型的属性值（默认值为0）
     */
	public static int getInt(Properties props, String key) {
		return getInt(props, key, 0);
	}
	
	/**
     * 获取int类型的属性值（可指定默认值）
     */
	public static int getInt(Properties props, String key, int defaultValue) {
		int value = defaultValue;
		if(props.contains(key))
			value = Integer.parseInt(props.getProperty(key));
		return value;
	}
	
	/**
     * 获取boolean类型的属性值（默认值为false）
     */
	public static boolean getBoolean(Properties props, String key) {
		return getBoolean(props, key, false);
	}
	
	/**
     * 获取boolean类型的属性值（可指定默认值）
     */
	public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if(props.contains(key))
			value = Boolean.parseBoolean(props.getProperty(key));
		return value;
	}
	
}

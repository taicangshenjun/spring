package com.spring.custom.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import com.spring.custom.annotation.CustomController;
import com.spring.custom.annotation.CustomRequestMapping;

public class CustomDispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//配置文件
	private Properties properties = new Properties();
	
	//需要实例化的bean
	private List<String> classNames = new ArrayList<String>();
	
	//ioc容器中存储bean的map
	private Map<String, Object> ioc = new HashMap<String, Object>();
	
	//映射处理器
	private Map<String, Method> handlerMapping = new HashMap<String, Method>();
	
	//
	private Map<String, Object> controllerMapping = new HashMap<String, Object>();
	
	private void doLoadConfig(String location) {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(resourceAsStream != null) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void doScanner(String packageName) {
		URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
		File dir = new File(url.getFile());
		for(File file: dir.listFiles()) {
			if(file.isDirectory())
				doScanner(file.getPath());
			else
				classNames.add(packageName + file.getName().replaceAll(".class", ""));
		}
	}
	
	private void doInstance() {
		if(classNames.isEmpty()) {
			System.out.println("classNames is empty!");
			return;
		}
		
		for(String className: classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				if(clazz.isAnnotationPresent(CustomController.class))
					ioc.put(toLowerFirstWord(clazz.getSimpleName()), clazz.newInstance());
				else
					continue;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void initHandlerMapping() {
		if(ioc.isEmpty()) {
			System.out.println("ioc container is empty!");
			return;
		}
		
		try {
			for(Entry<String, Object> entry: ioc.entrySet()) {
				Class<?> clazz = entry.getValue().getClass();
				if(!clazz.isAnnotationPresent(CustomController.class))
					continue;
				
				String baseUrl = "";
				if(clazz.isAnnotationPresent(CustomRequestMapping.class)) {
					CustomRequestMapping mapping = clazz.getAnnotation(CustomRequestMapping.class);
					baseUrl = mapping.value();
				}
				Method[] methods = clazz.getMethods();
				for(Method method: methods) {
					if(!method.isAnnotationPresent(CustomRequestMapping.class))
						continue;
					
					CustomRequestMapping annotation = method.getAnnotation(CustomRequestMapping.class);
					String url = annotation.value();
					
					url = (baseUrl + "" + url).replaceAll("+/", "/");
					handlerMapping.put(url, method);
					controllerMapping.put(url, clazz.newInstance());
					System.out.println(url + "," + method);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String toLowerFirstWord(String name) {
		char[] charArray = name.toCharArray();
		charArray[0] += 32;
		return String.valueOf(charArray);
	}
	

}

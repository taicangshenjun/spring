package com.spring.custom.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomController;
import com.spring.custom.annotation.CustomQualifier;
import com.spring.custom.annotation.CustomRequestMapping;
import com.spring.custom.annotation.CustomService;

public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//存储当前所有加载的类
	List<String> classNames = new ArrayList<String>();
	
	//存储IOC容器的MAP
	Map<String, Object> beans = new HashMap<String, Object>();
	
	//存储路径和方法的映射关系
	Map<String, Object> handlerMap = new HashMap<String, Object>();
	
	public DispatcherServlet() {
		System.out.println("DispatcherServlet()......");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init()......");
		
		//1.扫描需要的实例化的类
		doScanPackage("com.spring.custom");
		System.out.println("当前文件夹下所有的class类");
		for(String className: classNames) {
			System.out.println(className);
		}
		
		//2.实例化
		doInstance();
		System.out.println("当前实例化的对象信息......");
		for(Map.Entry<String, Object> map: beans.entrySet()) {
			System.out.println("key:" + map.getKey() + "; value:" + map.getValue());
		}
		
		//3.将IOC容器中的service对象设置给controller层定义的field上
		doIoc();
				
		//4.建立path与method的映射关系
		handlerMapping();
		System.out.println("Controller层的path和方法映射.........");
		for(Map.Entry<String, Object> map: handlerMap.entrySet()) {
			System.out.println("key:" + map.getKey() + "; value:" + map.getValue());
		}

	}
	
	private void doScanPackage(String basePackage) {
		URL resource = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
		String fileStr = resource.getFile();
		File file = new File(fileStr);
		String[] listFiles = file.list();
		for(String path: listFiles) {
			File filePath = new File(fileStr + path);
			// 如果当前是目录，则递归
			if(filePath.isDirectory())
				doScanPackage(basePackage + "." + path);
			// 如果是文件，则直接添加到classNames
			else
				classNames.add(basePackage + "." + filePath.getName());
		}
	}
	
	private void doInstance() {
		if(classNames.isEmpty()) {
			System.out.println("doScanner Fail....");
		}
		
		for(String className: classNames) {
			String cn = className.replaceAll(".class", "");
			try {
				Class<?> clazz = Class.forName(cn);
				if(clazz.isAnnotationPresent(CustomController.class)) {
					CustomRequestMapping requestMapping = clazz.getAnnotation(CustomRequestMapping.class);
					String key = requestMapping.value();
					Object value = clazz.newInstance();
					beans.put(key, value);
				}else if(clazz.isAnnotationPresent(CustomService.class)) {
					// 通过CustomService获取值，作为beans的key
					CustomService service = clazz.getAnnotation(CustomService.class);
					String key = service.value();
					
					// beans的vaue为实例化对象
					Object value = clazz.newInstance();
					beans.put(key, value);
				}else {
					continue;
				}
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void doIoc() {
		if(beans.isEmpty()) {
			System.out.println("no class is instance......");
			return;
		}
		
		for(Map.Entry<String, Object> map: beans.entrySet()) {
			Object instance = map.getValue();
			Class<?> clazz = instance.getClass();
			if(clazz.isAnnotationPresent(CustomController.class)) {
				Field[] fields = clazz.getDeclaredFields();
				for(Field field: fields) {
					if(field.isAnnotationPresent(CustomQualifier.class)) {
						// 获取当前成员变量的注解值
						CustomQualifier qualifier = field.getAnnotation(CustomQualifier.class);
						String value = qualifier.value();
						
						// 由于此类成员变量设置为private，需要强行设置
						field.setAccessible(true);
						
						// 将beans的实例化对象赋值给当前的变量
						try {
							field.set(instance, beans.get(value));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}else {
						continue;
					}
				}
			}
		}
		
		
		
		
		
	}
	
	private void handlerMapping() {
		
	}
	
}

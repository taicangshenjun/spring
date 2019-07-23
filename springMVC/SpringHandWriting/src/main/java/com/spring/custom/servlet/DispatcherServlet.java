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
	
	//�洢��ǰ���м��ص���
	List<String> classNames = new ArrayList<String>();
	
	//�洢IOC������MAP
	Map<String, Object> beans = new HashMap<String, Object>();
	
	//�洢·���ͷ�����ӳ���ϵ
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
		
		//1.ɨ����Ҫ��ʵ��������
		doScanPackage("com.spring.custom");
		System.out.println("��ǰ�ļ��������е�class��");
		for(String className: classNames) {
			System.out.println(className);
		}
		
		//2.ʵ����
		doInstance();
		System.out.println("��ǰʵ�����Ķ�����Ϣ......");
		for(Map.Entry<String, Object> map: beans.entrySet()) {
			System.out.println("key:" + map.getKey() + "; value:" + map.getValue());
		}
		
		//3.��IOC�����е�service�������ø�controller�㶨���field��
		doIoc();
				
		//4.����path��method��ӳ���ϵ
		handlerMapping();
		System.out.println("Controller���path�ͷ���ӳ��.........");
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
			// �����ǰ��Ŀ¼����ݹ�
			if(filePath.isDirectory())
				doScanPackage(basePackage + "." + path);
			// ������ļ�����ֱ����ӵ�classNames
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
					// ͨ��CustomService��ȡֵ����Ϊbeans��key
					CustomService service = clazz.getAnnotation(CustomService.class);
					String key = service.value();
					
					// beans��vaueΪʵ��������
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
						// ��ȡ��ǰ��Ա������ע��ֵ
						CustomQualifier qualifier = field.getAnnotation(CustomQualifier.class);
						String value = qualifier.value();
						
						// ���ڴ����Ա��������Ϊprivate����Ҫǿ������
						field.setAccessible(true);
						
						// ��beans��ʵ��������ֵ����ǰ�ı���
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

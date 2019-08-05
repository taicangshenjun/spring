package com.spring.custom.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//1.加载配置文件
		doLoadConfig(config.getInitParameter("contextConfigLocation"));
	   
		//2.初始化所有相关联的类,扫描用户设定的包下面所有的类
		doScanner(properties.getProperty("scanPackage"));
			   
		//3.拿到扫描到的类,通过反射机制,实例化,并且放到ioc容器中(k-v  beanName-bean) beanName默认是首字母小写
		doInstance();
			   
		//4.初始化HandlerMapping(将url和method对应上)
		initHandlerMapping();
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.doDispatch(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(handlerMapping.isEmpty())
			return;
		
		String url = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
		
		if(!this.handlerMapping.containsKey(url)) {
			resp.getWriter().write("404 NOT FOUND!");
			return;
		}
		
		Method method = this.handlerMapping.get(url);
		
		Class<?>[] paramTypes = method.getParameterTypes();
		Map<String, String[]> paramMap = req.getParameterMap();
		Object[] paramValues = new Object[paramTypes.length];
		
		for(int i = 0; i < paramTypes.length; i ++) {
			String requestParam = paramTypes[i].getSimpleName();
			
			if("HttpServletRequest".equals(requestParam)) {
				paramValues[i]=req;
				continue;
			}
			
			if (requestParam.equals("HttpServletResponse")){  
				paramValues[i]=resp;
				continue;  
			}
			
		   if(requestParam.equals("String")){
			   for (Entry<String, String[]> param : paramMap.entrySet()) {
				   String value =Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
				   paramValues[i]=value;
			   }
		   }
		}
		
		//利用反射机制来调用
		try {
			//第一个参数是method所对应的实例 在ioc容器中
			method.invoke(this.controllerMapping.get(url), paramValues);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void doLoadConfig(String location) {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(resourceAsStream != null) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
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

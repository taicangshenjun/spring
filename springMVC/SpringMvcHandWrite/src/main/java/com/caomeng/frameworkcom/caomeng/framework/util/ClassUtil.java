package com.caomeng.frameworkcom.caomeng.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * ��ȡ�������
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * ������
	 * @param className			����
	 * @param isInitialized		�Ƿ��ʼ��
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized){
		Class<?> cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		}catch(ClassNotFoundException e) {
			LOGGER.error("load class failure", e);
			throw new RuntimeException(e);
		}
		return cls;
	}
	
	/**
     * �����ࣨĬ�Ͻ���ʼ���ࣩ
     */
	public static Class<?> loadClass(String className){
		return loadClass(className, true);
	}
	
	/**
     * ��ȡָ�������µ�������
     */
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));	
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if(url != null) {
					String protocol = url.getProtocol();
					if("file".equals(protocol)) {
						String packagePath = url.getPath().replaceAll("%20", " ");
						addClass(classSet, packagePath, packageName);
					}else if("jar".equals(protocol)) {
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						if(jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if(jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while(jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if(!jarEntryName.endsWith(".class")) {
										String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return classSet;
	}
	
	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.isFile() && pathname.getName().endsWith(".class");
			}
		});
		for(File file: files) {
			String fileName = file.getName();
			if(file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if(StringUtils.isNotEmpty(packageName)) {
					className = packageName + "." + className;
				}
				doAddClass(classSet, className);
			}else {
				String subPackagePath = fileName;
				if(StringUtils.isNotEmpty(subPackagePath)) {
					subPackagePath = packageName + "/" + subPackagePath;
				}
				String subPackageName = fileName;
				if(StringUtils.isNotEmpty(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}
	
	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> cls = loadClass(className, false);
		classSet.add(cls);
	}
	
}

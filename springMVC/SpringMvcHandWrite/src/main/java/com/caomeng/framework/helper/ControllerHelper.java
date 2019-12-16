package com.caomeng.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.caomeng.framework.annotation.RequestMapping;
import com.caomeng.framework.bean.Handler;
import com.caomeng.framework.bean.Request;

/**
 * ControllerHelper �����ඨ����һ��"����-������" ��ӳ�� REQUEST_MAP, 
 * REQUEST_MAP ���൱��Spring MVC���ӳ�䴦����, 
 * ���յ�����󷵻ض�Ӧ�Ĵ�����.REQUEST_MAP ӳ�䴦������ʵ���߼�����:
 * ����ͨ�� ClassHelper �������ȡ��Ӧ��������Controller��Class����, 
 * Ȼ�����Controller�������з���, �����д� @RequestMapping ע��ķ�����װΪ������, 
 * �� @RequestMapping ע���������·�������󷽷���װ���������, Ȼ����� REQUEST_MAP ��.
 * @author cm
 *
 */
public final class ControllerHelper {

	/**
	 * REQUEST_MAPΪ "����-������" ��ӳ��
	 */
	private static final Map<Request, Handler> REQUEST_MAP = new HashMap<Request, Handler>();
	
	static {
		Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();
		if(CollectionUtils.isNotEmpty(controllerSet)) {
			for(Class<?> controller: controllerSet) {
				//��ȡ���й�������
				Method[] methods = controller.getMethods();
				if(ArrayUtils.isNotEmpty(methods)) {
					for(Method method: methods) {
						if(method.isAnnotationPresent(RequestMapping.class)) {
							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							 //���󷽷�
                            String requestMethod = requestMapping.method().name();
							//����·��
                            String requestPath = requestMapping.value();

                            //��װ����ʹ�����
                            Request request = new Request(requestMethod, requestPath);
                            Handler handler = new Handler(controller, method);
                            REQUEST_MAP.put(request, handler);
						}
					}
				}
			}
		}
	}
	
	/**
	 * ��ȡHandler
	 * @param requestMethod
	 * @param requestPath
	 * @return
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return REQUEST_MAP.get(request);
	}
	
}

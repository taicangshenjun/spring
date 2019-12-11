package com.caomeng.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.caomeng.framework.bean.Data;
import com.caomeng.framework.bean.Handler;
import com.caomeng.framework.bean.Param;
import com.caomeng.framework.bean.View;
import com.caomeng.framework.helper.BeanHelper;
import com.caomeng.framework.helper.ConfigHelper;
import com.caomeng.framework.helper.ControllerHelper;
import com.caomeng.framework.helper.RequestHelper;
import com.caomeng.framework.util.ReflectionUtil;

/**
 * ǰ�˿�����ʵ������һ��Servlet, �������õ���������������, �ڷ���������ʱʵ����.
 * ��DispatcherServletʵ����ʱ, ����ִ�� init() ����, ��ʱ����� HelperLoader.init() ������������ص�helper��, 
 * ��ע�ᴦ����Ӧ��Դ��Servlet.
 * ����ÿһ�οͻ������󶼻�ִ�� service() ����, ��ʱ�����Ƚ����󷽷�������·����װΪRequest����, 
 * Ȼ���ӳ�䴦���� (REQUEST_MAP) �л�ȡ��������. Ȼ��ӿͻ��������л�ȡ��Param��������, ִ�д���������. 
 * ����жϴ����������ķ���ֵ, ��Ϊview����, ����ת��jspҳ��, ��Ϊdata����, �򷵻�json����.
 * @author cm
 *
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestMethod = request.getMethod().toUpperCase();
		String requestPath = request.getPathInfo();
		
		//�������Tomcat������·�����������, һ���� "/userList", ��һ���� "/context��ַ/userList".
		String[] splits = requestPath.split("/");
		if(splits.length > 2)
			requestPath = "/" + splits[2];
		
		//���������ȡ������(����������SpringMVC�е�ӳ�䴦����)
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler != null) {
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			
			//��ʼ������
			Param param = RequestHelper.createParam(request);
			
			//�����������Ӧ�ķ���(����������SpringMVC�еĴ�����������)
			Object result = null;
			Method actionMethod = handler.getControllerMethod();
			if(param == null || param.isEmpty()) {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			}else {
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			}
			
			//��תҳ��򷵻�json����(����������SpringMVC�е���ͼ������)
            if (result instanceof View) {
            	try {
					handleViewResult((View) result, request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else if (result instanceof Data) {
                handleDataResult((Data) result, response);
            }
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		//��ʼ����ص�helper��
		HelperLoader.init();
		
		//��ȡServletContext��������ע��Servlet
		ServletContext servletContext = config.getServletContext();
		
		//ע�ᴦ��jsp�;�̬��Դservlet
		registerServlet(servletContext);
	}
	
	/**
	 * 
	 * @param servletContext
	 */
	private void registerServlet(ServletContext servletContext) {
		//ServletRegistrationע�����򣬿������·��
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		
		//��̬ע�ᴦ��̬��Դ��Ĭ��Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		//ͷ����վ
		defaultServlet.addMapping("/favicon.ico");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		
	}
	
	/**
     * ��תҳ��
     */
	private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = view.getPath();
		if(StringUtils.isNotEmpty(path)) {
			if(path.startsWith("/")) {
				response.sendRedirect(request.getContextPath() + path);
			}else {
				Map<String, Object> model = view.getModel();
				for(Map.Entry<String, Object> entry: model.entrySet())
					request.setAttribute(entry.getKey(), entry.getValue());
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
			}
		}
	}

	/**
	 * ����json����
	 * @param data
	 * @param response
	 */
	private void handleDataResult(Data data, HttpServletResponse response) {
		Object model = data.getData();
		if(model != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				String json = JSON.toJSON(model).toString();
				pw.write(json);
				pw.flush();
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				if(pw != null) {
					pw.close();
				}
			}
		}
	}
	
}

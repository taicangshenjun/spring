package com.caomeng.framework;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.caomeng.framework.bean.Data;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		HelperLoader.init();
	}

	/**
	 * ·µ»ØjsonÊý¾Ý
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

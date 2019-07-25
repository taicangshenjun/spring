package com.spring.custom.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomController;
import com.spring.custom.annotation.CustomQualifier;
import com.spring.custom.annotation.CustomRequestMapping;
import com.spring.custom.service.MyService;

@CustomController
@CustomRequestMapping("/custom")
public class MyController {
	
	@CustomQualifier("myServiceImpl")
	private MyService myService;
	
	public void query(HttpServletRequest request, HttpServletResponse response, String name, String age) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			String result = myService.query(name, age);
			pw.write(result);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}

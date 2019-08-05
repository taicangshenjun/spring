package com.spring.custom.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.custom.annotation.CustomController;
import com.spring.custom.annotation.CustomRequestMapping;

@CustomController("testController")
@CustomRequestMapping("/test")
public class TestController {
	
	public void test(HttpServletRequest request, HttpServletResponse response, String param) {
		System.out.println(param);
		try {
			response.getWriter().write("doTest method success! param:" + param);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

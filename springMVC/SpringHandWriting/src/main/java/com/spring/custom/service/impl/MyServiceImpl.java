package com.spring.custom.service.impl;

import com.spring.custom.annotation.CustomService;
import com.spring.custom.service.MyService;

@CustomService("myServiceImpl")
public class MyServiceImpl implements MyService {

	public String query(String name, String age) {
		return "name:" + name + "; age:" + age;
	}

}

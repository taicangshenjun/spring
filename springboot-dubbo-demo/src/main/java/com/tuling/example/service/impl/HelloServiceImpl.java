package com.tuling.example.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.tuling.example.service.HelloSerice;

@Service
@DubboService
public class HelloServiceImpl implements HelloSerice {

	@Override
	public String sayHello(String name) {
		return "hello " + name;
	}

}

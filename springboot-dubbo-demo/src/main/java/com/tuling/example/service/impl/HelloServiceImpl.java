package com.tuling.example.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.tuling.example.service.HelloService;

//@DubboService(
//		version = "1.0.0",
//        application = "${dubbo.application.id}",
//        protocol = "${dubbo.protocol.id}",
//        registry = "${dubbo.registry.id}")
@DubboService(protocol = "dubbo")
@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "hello " + name + "！";
	}

}

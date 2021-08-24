package com.tuling.mall.userconsumer.controller;

import java.util.Map;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.common.service.OrderService;

/**
 * @author Fox
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@DubboReference(protocol = "dubbo")
	private OrderService orderService;
	
	@RequestMapping("/test/{id}")
    public Map<String, Object> test(@PathVariable("id") Integer id){
    	return orderService.test(id);
    }
	
}

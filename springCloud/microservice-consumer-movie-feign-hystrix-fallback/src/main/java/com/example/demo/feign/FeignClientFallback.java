package com.example.demo.feign;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

@Component
public class FeignClientFallback implements UserFeignClient {

	@Override
	public User findById(String id) {
		User user = new User();
		user.setId("-1");
		user.setUserName("account0");
		user.setName("默认用户");
		return user;
	}

}

package com.example.demo.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

import feign.hystrix.FallbackFactory;

@Component
public class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

	private static final Logger logger = LoggerFactory.getLogger(FeignClientFallbackFactory.class);
	
	@Override
	public UserFeignClient create(Throwable cause) {
		return new UserFeignClient() {
			
			@Override
			public User findById(String id) {
				logger.info("fallback; reason was: ", cause);
				User user = new User();
				user.setId("-1");
				user.setUserName("account0");
				user.setName("默认用户");
				return user;
			}
			
		};
	}

}

package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController("movieController")
@RequestMapping("/movieController")
public class MovieController {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	/**
	 * request头,application/json;charset=UTF-8返回json
	 * 
	 * @HystrixCommand编辑回退方法findByIdFallback
	 * @param id
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "findByIdFallback")
	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findById(@PathVariable("id") String id) {
		String url = "http://microservice-provider-user/userController/findById/" + id;
		return this.restTemplate.getForObject(url, User.class);
	}
	
	/**
	 * 回退方法findByIdFallback，
	 * 该方法与findById具有相同的参数和返回值。
	 * 
	 * 强调：回退不代表断路器打开，失败达到阈值（默认5秒20次失败），断路器才会打开。
	 * @param id
	 * @return
	 */
	public User findByIdFallback(String id) {
		User user = new User();
		user.setId("-1");
		user.setUserName("account0");
		user.setName("默认用户");
		return user;
	}
	
	@GetMapping("/log-instance")
	public void logUserInstance() {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
		logger.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
	}
	
	@GetMapping("/user-instance")
	public List<ServiceInstance> showInfo(){
		return this.discoveryClient.getInstances("microservice-provider-user");
	}
	
}

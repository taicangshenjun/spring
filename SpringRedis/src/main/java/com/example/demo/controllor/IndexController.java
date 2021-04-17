package com.example.demo.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@RequestMapping("/test_sentinel")
	public void testSentinel() throws InterruptedException {
		int i = 1;
		while(true) {
			try {
				stringRedisTemplate.opsForValue().set("zhuge" + i, i + "");
				System.out.println("设置key："+ "zhuge" + i);
				i ++;
				Thread.sleep(1000);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}

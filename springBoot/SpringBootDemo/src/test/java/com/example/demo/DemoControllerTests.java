package com.example.demo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.controller.DemoController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoControllerTests {
	
	private MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(new DemoController()).build();
	}
	
	@Test
	public void test() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content=mvcResult.getResponse().getContentAsString();
		
		//断言
		Assert.assertEquals(200, status);
		Assert.assertEquals("hello world!", content);
	}

}

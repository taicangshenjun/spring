package com.example.demo.aop.ealyAopDemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.aop.Calculate;

public class TulingMainClass {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(EalyAopMainConfig.class);
//		Calculate calculateProxy = ctx.getBean("calculateProxy", Calculate.class);
		Calculate tulingCalculate = ctx.getBean("tulingCalculate", Calculate.class);
		tulingCalculate.div(1,1);
	}
	
}

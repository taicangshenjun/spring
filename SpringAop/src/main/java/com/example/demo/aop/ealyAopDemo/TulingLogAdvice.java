package com.example.demo.aop.ealyAopDemo;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.aop.MethodBeforeAdvice;

public class TulingLogAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		String methodName = method.getName();
		System.out.println("执行目标方法【" + methodName + "】的<前置通知>,入参" + Arrays.asList(args));
	}

}

package com.example.demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security5中，自带的NoOPPasswordEncoder已被废弃
 * 我们新建一个PasswordEncoder的实现类，完成不加密的功能
 * @author cm
 *
 */
public class MyNoOpPasswordEncoder implements PasswordEncoder {
	
	private static final MyNoOpPasswordEncoder INSTANCE = new MyNoOpPasswordEncoder();
	
	private MyNoOpPasswordEncoder() {
		
	}
	
	public static MyNoOpPasswordEncoder getInstance() {
		return INSTANCE;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

	
	
}

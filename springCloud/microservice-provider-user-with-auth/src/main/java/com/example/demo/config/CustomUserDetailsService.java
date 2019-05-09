package com.example.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	/**
	 * 模拟两个账户：
	 * 1.账号是user,密码password1，角色是user-role
	 * 2.账号是admin,密码password2，角色是admin-role
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("user".equals(username)) {
			return new SecurityUser("user", "password1", "user-role");
		}else if("admin".equals(username)) {
			return new SecurityUser("admin", "password2", "admin-role");
		}else {
			return null;
		}
	}

}

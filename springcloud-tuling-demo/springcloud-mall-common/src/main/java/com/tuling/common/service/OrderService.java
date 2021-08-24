package com.tuling.common.service;

import java.util.List;
import java.util.Map;

import com.tuling.common.entity.OrderEntity;

public interface OrderService {

	public List<OrderEntity> findOrderByUserId(String userId);
	
	public Map<String, Object> test(Integer id);
	
}

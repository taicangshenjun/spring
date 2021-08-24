package com.tuling.mall.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuling.common.entity.OrderEntity;
import com.tuling.common.entity.UserEntity;
import com.tuling.common.service.OrderService;
import com.tuling.mall.order.dao.OrderDao;
import com.tuling.mall.order.dao.UserDao;

@Service("orderService")
@DubboService(protocol = "dubbo")
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<OrderEntity> findOrderByUserId(String userId) {
		List<OrderEntity> result = orderDao.findOrderByUserId(userId);
		return result;
	}


	@Override
	public Map<String, Object> test(Integer id) {
		OrderEntity order = orderDao.selectById(id);
		UserEntity user = userDao.selectById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order", order);
		map.put("user", user);
		return map;
	}

}

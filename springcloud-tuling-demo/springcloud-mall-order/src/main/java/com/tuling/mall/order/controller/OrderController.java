package com.tuling.mall.order.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuling.common.entity.OrderEntity;
import com.tuling.common.service.OrderService;
import com.tuling.common.utils.R;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 *
 * @author fox
 * @email 2763800211@qq.com
 * @date 2021-01-28 15:46:19
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
	
	@Resource
	@Qualifier("orderService")
	private OrderService orderService;
	
    /**
     * 根据用户id查询订单信息
     * @param userId
     * @return
     */
    @RequestMapping("/findOrderByUserId/{userId}")
    public R findOrderByUserId(@PathVariable("userId") String userId) {
        System.out.println("根据userId:" + userId + "查询订单信息");
        List<OrderEntity> orderEntities = orderService.findOrderByUserId(userId);
        return R.ok().put("orders", orderEntities);
    }
    
    @RequestMapping("/test/{id}")
    public Map<String, Object> test(@PathVariable("id") Integer id){
    	return orderService.test(id);
    }
    
}

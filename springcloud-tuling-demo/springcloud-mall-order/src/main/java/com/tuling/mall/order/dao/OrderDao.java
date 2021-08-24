package com.tuling.mall.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.common.entity.OrderEntity;

@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
	public List<OrderEntity> findOrderByUserId(@Param("userId") String userId);

}

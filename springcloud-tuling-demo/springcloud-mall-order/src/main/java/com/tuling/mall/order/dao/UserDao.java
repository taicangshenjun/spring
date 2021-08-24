package com.tuling.mall.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.common.entity.UserEntity;

@Mapper
@DS("tuling")
public interface UserDao extends BaseMapper<UserEntity> {

}

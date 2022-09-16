package com.leechr.meituan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leechr.meituan.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}
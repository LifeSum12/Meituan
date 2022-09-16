package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.OrderDetail;
import com.leechr.meituan.mapper.OrderDetailMapper;
import com.leechr.meituan.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
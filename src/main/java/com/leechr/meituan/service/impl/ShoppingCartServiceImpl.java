package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.ShoppingCart;
import com.leechr.meituan.mapper.ShoppingCartMapper;
import com.leechr.meituan.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}

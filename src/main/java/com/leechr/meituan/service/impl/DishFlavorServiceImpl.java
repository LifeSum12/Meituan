package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.DishFlavor;
import com.leechr.meituan.mapper.DishFlavorMapper;
import com.leechr.meituan.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

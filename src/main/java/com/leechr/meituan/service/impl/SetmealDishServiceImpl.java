package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.dto.SetmealDto;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.entity.SetmealDish;
import com.leechr.meituan.mapper.SetmealDishMapper;
import com.leechr.meituan.service.CategoryService;
import com.leechr.meituan.service.SetmealDishService;
import com.leechr.meituan.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}

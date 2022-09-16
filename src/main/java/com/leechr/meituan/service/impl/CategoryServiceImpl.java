package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.common.CustomException;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Dish;
import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.mapper.CategoryMapper;
import com.leechr.meituan.service.CategoryService;
import com.leechr.meituan.service.DishService;
import com.leechr.meituan.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //返回异常
            throw new CustomException("当前分类关联了菜品，不能删除！");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //返回异常
            throw new CustomException("当前分类关联了套餐，不能删除！");
        }

        super.removeById(id);

    }
}


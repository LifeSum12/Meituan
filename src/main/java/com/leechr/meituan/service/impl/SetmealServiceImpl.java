package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.common.CustomException;
import com.leechr.meituan.dto.SetmealDto;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.entity.SetmealDish;
import com.leechr.meituan.mapper.SetmealMapper;
import com.leechr.meituan.service.CategoryService;
import com.leechr.meituan.service.SetmealDishService;
import com.leechr.meituan.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public SetmealDto getWithDish(Long id) {
        //ids为setmeal的id
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = this.getById(id);
        //1.查询setmealDish表的信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        //2.查询category表的信息
        Category category = categoryService.getById(setmeal.getCategoryId());

        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
        setmealDto.setCategoryName(category.getName());
        return setmealDto;
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //接收到的ids为setmeal的id
        //0.先判断status，若为1，不能删除
        LambdaQueryWrapper<Setmeal> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Setmeal::getId,ids)
                .eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper1);
        if(count>0){
            throw new CustomException("套餐正在售卖中，不能删除！");
        }
        //1.先删除setmeal表的数据
        this.removeByIds(ids);
        //2.再删除setmealDish表数据
        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper2);
    }
}

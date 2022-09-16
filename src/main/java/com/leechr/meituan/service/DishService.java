package com.leechr.meituan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leechr.meituan.dto.DishDto;
import com.leechr.meituan.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    //在添加菜品，同时插入口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据Id在获取菜品，同时获取口味数据
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void deleteWithFlavor(List<Long> ids);

}

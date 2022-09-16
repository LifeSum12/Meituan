package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.common.R;
import com.leechr.meituan.dto.DishDto;
import com.leechr.meituan.entity.Dish;
import com.leechr.meituan.entity.DishFlavor;
import com.leechr.meituan.mapper.DishMapper;
import com.leechr.meituan.service.DishFlavorService;
import com.leechr.meituan.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //1.把dishdto中的菜品信息保存到菜品表
        this.save(dishDto);
        //2.把dishdto中的口味信息保存到口味表
        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId((dishId));
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish基本信息
        this.updateById(dishDto);
        //清理当前菜品对应的口味数据--delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //添加当前提交过来的口味数据--insert
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId((dishDto.getId()));
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }


    public void deleteWithFlavor(List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Dish::getId,ids);
        LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(DishFlavor::getDishId,ids);

        dishService.remove(queryWrapper1);
        dishFlavorService.remove(queryWrapper2);
    }


}

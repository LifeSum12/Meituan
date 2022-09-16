package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leechr.meituan.common.R;
import com.leechr.meituan.dto.DishDto;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Dish;
import com.leechr.meituan.entity.DishFlavor;
import com.leechr.meituan.service.CategoryService;
import com.leechr.meituan.service.DishFlavorService;
import com.leechr.meituan.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功！");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, lambdaQueryWrapper);

        //把dish的page对象，赋给dishDto的page对象并且给categoryName赋值
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //把每个dish对象全部值赋给dishDto对象，但categoryName值仍为空
            BeanUtils.copyProperties(item, dishDto);
            //通过CategoryId查询到Category对象
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                //把Category对象的Name值赋给dishDto对象
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功！");
    }

    /**
     * 启售or停售
     *
     * @param n
     * @param ids
     * @return
     */
    @PostMapping("/status/{n}")
    public R<String> updateStatus(@PathVariable int n, Long ids) {
        Dish dish = new Dish();
        dish.setStatus(n);
        dish.setId(ids);
        dishService.updateById(dish);
        return R.success("状态修改成功！");
    }

    /**
     * 删除单个菜品、批量删除菜品
     *
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.deleteWithFlavor(ids);
        return R.success("删除成功！");
    }

//    /**
//     * 根据条件查询对应菜品数据
//     *
//     * @param dish
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish) {
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        queryWrapper.eq(Dish::getStatus, 1);
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }

    /**
     * 根据条件查询对应菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //把每个dish对象全部值赋给dishDto对象，但categoryName值仍为空
            BeanUtils.copyProperties(item, dishDto);
            //通过CategoryId查询到Category对象
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                //把Category对象的Name值赋给dishDto对象
                dishDto.setCategoryName(category.getName());
            }
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> flavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            flavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(flavorLambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dtoList);
    }
}

package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leechr.meituan.common.R;
import com.leechr.meituan.dto.SetmealDto;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Setmeal;
import com.leechr.meituan.entity.SetmealDish;
import com.leechr.meituan.service.CategoryService;
import com.leechr.meituan.service.SetmealDishService;
import com.leechr.meituan.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功！");
    }

    /**
     * 修改页面的回显功能
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable long id){
        SetmealDto setmealDto = setmealService.getWithDish(id);
        return R.success(setmealDto);
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name)
                .orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(categoryId!=null){
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
       setmealService.removeWithDish(ids);
       return R.success("删除成功！");
    }


    @PostMapping("/status/{n}")
    public R<String> updateStatus(@PathVariable int n, Long ids){
        Setmeal setmeal = new Setmeal();
        setmeal.setId(ids);
        setmeal.setStatus(n);
        setmealService.updateById(setmeal);
        return R.success("状态修改成功！");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId())
                .eq(Setmeal::getStatus,1)
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }


}

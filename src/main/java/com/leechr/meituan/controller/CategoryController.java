package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leechr.meituan.common.R;
import com.leechr.meituan.entity.Category;
import com.leechr.meituan.entity.Employee;
import com.leechr.meituan.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功！");
    }

    /**
     * 分页功能
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        //categoryService.removeById(id);
        categoryService.remove(ids);

        return R.success("分类信息删除成功！");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("分类信息修改成功！");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){

        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        lambdaQueryWrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }


}

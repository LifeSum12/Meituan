package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leechr.meituan.common.BaseContext;
import com.leechr.meituan.common.R;
import com.leechr.meituan.entity.ShoppingCart;
import com.leechr.meituan.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查询当前菜品or套餐，是否已经存在
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        if (shoppingCart.getSetmealId() != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //若存在则number+1
        ShoppingCart newShoppingCart = shoppingCartService.getOne(queryWrapper);
        if (newShoppingCart != null) {
            newShoppingCart.setNumber(newShoppingCart.getNumber() + 1);
            shoppingCartService.updateById(newShoppingCart);
        }
        //若不存在，则新增
        if (newShoppingCart == null) {
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            newShoppingCart = shoppingCart;
        }
        return R.success(newShoppingCart);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查询当前菜品or套餐，是否已经存在
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (shoppingCart.getDishId() != null) {
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        if (shoppingCart.getSetmealId() != null) {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        //存在
        ShoppingCart newShoppingCart = shoppingCartService.getOne(queryWrapper);
        //属性number=number-1
        newShoppingCart.setNumber(newShoppingCart.getNumber() - 1);
        //若number>=1
        if (newShoppingCart.getNumber() >= 1) {
            shoppingCartService.updateById(newShoppingCart);
            return R.success(newShoppingCart);
        }
        //若number=0,则删除
        shoppingCartService.removeById(newShoppingCart.getId());
        return R.success(newShoppingCart);
    }

    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @DeleteMapping("clean")
    public R<String> clean(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(queryWrapper);
        return R.success("购物车清空成功！");
    }

}








package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leechr.meituan.common.BaseContext;
import com.leechr.meituan.common.R;
import com.leechr.meituan.dto.DishDto;
import com.leechr.meituan.dto.OrdersDto;
import com.leechr.meituan.entity.OrderDetail;
import com.leechr.meituan.entity.Orders;
import com.leechr.meituan.entity.User;
import com.leechr.meituan.service.OrderDetailService;
import com.leechr.meituan.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        orderService.submit(orders);
        return R.success("下单成功！");
    }

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        //访问的是外卖用户
        Page newPage = orderService.pageUser(page, pageSize);
        return R.success(newPage);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,Long number) {
        //后台管理员访问
        Page newPage = orderService.pageEmployee(page, pageSize,number);
        return R.success(newPage);
    }
}

package com.leechr.meituan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leechr.meituan.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
    public Page pageUser(int page,int pageSize);
    public Page pageEmployee(int page,int pageSize,Long number);

}

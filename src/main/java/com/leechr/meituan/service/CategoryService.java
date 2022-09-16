package com.leechr.meituan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leechr.meituan.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

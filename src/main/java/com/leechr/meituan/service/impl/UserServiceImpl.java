package com.leechr.meituan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.User;
import com.leechr.meituan.mapper.UserMapper;
import com.leechr.meituan.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

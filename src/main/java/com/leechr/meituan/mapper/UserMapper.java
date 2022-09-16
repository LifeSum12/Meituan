package com.leechr.meituan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leechr.meituan.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

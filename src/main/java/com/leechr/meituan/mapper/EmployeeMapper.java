package com.leechr.meituan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leechr.meituan.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

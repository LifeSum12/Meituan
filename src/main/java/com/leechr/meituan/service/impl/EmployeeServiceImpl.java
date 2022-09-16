package com.leechr.meituan.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leechr.meituan.entity.Employee;
import com.leechr.meituan.mapper.EmployeeMapper;
import com.leechr.meituan.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}

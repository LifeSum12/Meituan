package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leechr.meituan.common.R;
import com.leechr.meituan.entity.Employee;
import com.leechr.meituan.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录功能
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1.页面提交的密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.先根据提交用户名(唯一)查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3.登录失败
        if(emp == null){
            return R.error("登陆失败，用户名不存在！");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("登陆失败，密码错误！");
        }
        if(emp.getStatus() == 0){
            return R.error("登陆失败，账号已禁用！");
        }
        //4.登陆成功
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 退出登录功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    /**
     * 新增用户功能
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //设置创建人
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功！");
    }

    /**
     * 用户页面展示、翻页功能
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name){

        Page pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //先判断name是否为空，为空不查数据库。否则通过name进行查询
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        //按创建时间排序展示
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询，结果返回给pageInfo
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 用户状态启动or禁用、用户信息修改功能
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateUser(empId);
        //employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("员工信息修改成功！");
    }

    /**
     * 通过id获取用户信息功能（修改用户信息时回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }

}

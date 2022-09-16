package com.leechr.meituan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.leechr.meituan.common.R;
import com.leechr.meituan.entity.User;
import com.leechr.meituan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(@RequestBody User one, HttpServletRequest request){
        String phone = one.getPhone();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user = userService.getOne(queryWrapper);
        //如果不存在，创建该用户
        if(user == null) {
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userService.save(user);
        }
        if(user.getStatus() == 0){
            return R.error("登陆失败，手机号已禁用！");
        }
        //登陆成功
        request.getSession().setAttribute("user",user.getId());
        return R.success(user);
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("退出登录成功！");
    }

}

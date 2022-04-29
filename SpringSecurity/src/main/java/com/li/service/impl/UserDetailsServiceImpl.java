package com.li.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.li.domain.LoginUser;
import com.li.domain.User;
import com.li.mapper.MenuMapper;
import com.li.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 通过实现框架的UserDetailsService接口，自定义自己的查询用户方法
 * @author: li
 * @create: 2022-04-25 20:36
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        //没有查询到用户抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }


        //TODO 查询对应的权限信息
        List<String> list = menuMapper.selectPermsByUserId(user.getId());



        //将数据封装成UserDetails对象
        return new LoginUser(user,list);
    }
}

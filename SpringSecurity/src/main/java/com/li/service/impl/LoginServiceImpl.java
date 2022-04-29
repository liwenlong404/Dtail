package com.li.service.impl;

import com.li.domain.LoginUser;
import com.li.domain.ResponseResult;
import com.li.domain.User;
import com.li.service.LoginService;
import com.li.utils.JwtUtil;
import com.li.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-25 21:48
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //认证没通过，给出提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        //认证通过，使用userId生成jwt，jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        Map<String, String> map = new HashMap<>(5);
        map.put("token", jwt);
        //将完整的用户信息存入redis，userId作为key
        redisCache.setCacheObject("login" + userId, loginUser);

        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //获取存入SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //删除redis的值
        Long id = loginUser.getUser().getId();
        redisCache.deleteObject("login"+id);


        return new ResponseResult(200, "注销成功");
    }
}





































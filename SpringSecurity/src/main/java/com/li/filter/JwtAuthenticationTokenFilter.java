package com.li.filter;

import com.li.domain.LoginUser;
import com.li.utils.JwtUtil;
import com.li.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description: jwt认证过滤器
 * @author: li
 * @create: 2022-04-26 21:55
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;


    /**
     * @Author: Li
     * @Description: 每一次请求都会验证，获取token，解析，通过userId从redis中获取用户信息
     * @DateTime: 2022/4/29 18:55
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)){
            //token没有，放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
             userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //从redis中获取用户信息
        String redisKey="login"+userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)){
            //如果login为null
            throw new RuntimeException("用户未登陆");
        }


        //存入SecurityContextHolder
        //TODO 获取权限信息，封装到Authentication
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(loginUser, null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

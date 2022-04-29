package com.li.handler;

import com.alibaba.fastjson.JSON;
import com.li.domain.ResponseResult;
import com.li.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-29 21:48
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       ResponseResult responseResult= new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败");
        String json = JSON.toJSONString(responseResult);

        //处理异常
        WebUtils.renderString(response, json);
    }
}

package com.li.expression;

import com.li.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-29 22:50
 */
@Component("ex")
public class ExpressionRoot {

    /**
     *
     * @author: Li
     * @dateTime: 2022/4/29 22:51
     */
    public boolean hasAuthority(String authority){
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();

        //判断用户权限集合中是否中有用户权限
        return permissions.contains(authority);
    }
}

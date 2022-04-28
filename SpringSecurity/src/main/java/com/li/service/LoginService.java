package com.li.service;

import com.li.domain.ResponseResult;
import com.li.domain.User;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-25 21:47
 */
public interface LoginService {
    ResponseResult login(User user);
}

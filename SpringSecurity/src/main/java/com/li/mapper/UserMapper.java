package com.li.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-25 19:01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

package com.li.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-29 21:07
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询权限集合
     * @author: Li
     * @dateTime: 2022/4/29 21:10
     * @param userId 用户id
     * @return 权限集合
     */
   @Select("SELECT DISTINCT m.`perms` FROM sys_user_role ur LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id` LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id` LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id` WHERE user_id = ${userId} AND r.`status` = 0 AND m.`status` = 0")
    List<String> selectPermsByUserId(long userId);

}

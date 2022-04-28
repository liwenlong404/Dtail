package com.li;

import com.li.domain.User;
import com.li.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @Description:
 * @author: li
 * @create: 2022-04-25 20:21
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);

        System.out.println(users);
    }

    @Test
    public void testBCryptPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        //$2a$10$xa.YgmAsrdMVSMUw1w6UO.jV.DOfukBg72MpFZ/YOyuFCYUXzb8ju
        //$2a$10$qpsah3zRJs3Ich.VRep.O..NoFYWY9Wqq9JbWjHnqhyKScX7Gi3ki
        boolean matches = passwordEncoder.matches("1234", "$2a$10$qpsah3zRJs3Ich.VRep.O..NoFYWY9Wqq9JbWjHnqhyKScX7Gi3ki");
        System.out.println(matches);

    }
}

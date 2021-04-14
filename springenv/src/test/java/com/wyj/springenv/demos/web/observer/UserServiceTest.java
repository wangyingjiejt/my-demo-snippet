package com.wyj.springenv.demos.web.observer;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author W.Y.J
 * @Date 2021/4/14 23:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends TestCase {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() {

        userService.register("令狐冲");
    }
}
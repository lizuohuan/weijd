package com.magic.weijd.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.magic.weijd.WeijdApplication;
import com.magic.weijd.entity.User;
import com.magic.weijd.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author lzh
 * @create 2017/10/31 15:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WeijdApplication.class)
public class UserControllerTest {

    @Resource
    private UserService userService;

    @Test
    public void testFindUserCreditById() throws Exception {
        User u = userService.findUserCreditById(77);
        System.out.println(JSON.toJSON(u));
    }
}
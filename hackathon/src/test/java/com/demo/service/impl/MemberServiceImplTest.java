package com.demo.service.impl;

import com.demo.HackathonApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HackathonApplication.class})
public class MemberServiceImplTest {

    @Autowired
    private MemberServiceImpl memberService;

    @Test
    public void testRegister() {
        memberService.register("Hongbo", "Xiao", "13132280603", "xiaohongbo@tju.edu.cn", "123456");
    }
}
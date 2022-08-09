package com;

import MyBatisThorough.mybatis5.service.TestService;
import MyBatisThorough.mybatis5.config.MainConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MainConfiguration.class)
public class MainTest {
    @Resource
    TestService service;

    @Test
    public void test(){
        service.setStudent();
    }
}

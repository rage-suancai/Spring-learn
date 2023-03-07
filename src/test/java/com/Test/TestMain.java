package com.Test;

import DatabaseFrameworkIntegration.mybatisTransaction.Service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author YXS
 * @PackageName: com
 * @ClassName: Main
 * @Desription:
 * @date 2023/3/6 19:55
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class TestMain {

    @Autowired
    TestService service;

    @Test
    public void test() {

        service.test();

    }

}

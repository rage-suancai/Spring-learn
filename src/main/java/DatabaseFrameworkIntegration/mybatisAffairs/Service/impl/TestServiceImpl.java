package DatabaseFrameworkIntegration.mybatisAffairs.Service.impl;

import DatabaseFrameworkIntegration.mybatisAffairs.Service.TestService;
import DatabaseFrameworkIntegration.mybatisAffairs.mapper.TestMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YXS
 * @PackageName: DatabaseFrameworkIntegration.mybatisAffairs.Service.impl
 * @ClassName: TestServiceImpl
 * @Desription:
 * @date 2023/3/6 16:57
 */
@Component
public class TestServiceImpl implements TestService {

    @Resource
    TestMapper mapper;

    @Override
    @Transactional
    public void test() {

        /*mapper.insertDept("50", "地质科研部", "挪威");
        if (true) throw new RuntimeException("我是测试异常");
        mapper.insertDept("50", "地质科研部", "挪威");*/

        test2();
        //if (true) throw new RuntimeException("我是测试异常");

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void test2() {

        mapper.insertDept("50", "地质科研部", "挪威");
        if (true) throw new RuntimeException("我是测试异常");

    }

}

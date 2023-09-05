package Spring.Mybatis.service.impl;

import Spring.Mybatis.mapper.TestMapper;
import Spring.Mybatis.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testmapper;

    @Transactional
    @Override
    public void test() {

        testmapper.insertStudent();
        if (true) throw new RuntimeException("我是测试异常");
        testmapper.insertStudent();

    }

}

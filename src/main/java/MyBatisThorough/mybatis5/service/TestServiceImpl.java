package MyBatisThorough.mybatis5.service;

import MyBatisThorough.mybatis5.mapper.TestMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class TestServiceImpl implements TestService{

    @Resource
    TestMapper mapper;

    @Transactional
    @Override
    public void setStudent() {
        //mapper.insertStudent();
        setStudent2();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void setStudent2() {
        mapper.insertStudent();
        throw new RuntimeException("我是事务2的一个异常! ");
    }

}

package MyBatisThorough.mybatis3.service;

import MyBatisThorough.mybatis3.bean.Student;
import MyBatisThorough.mybatis3.mapper.TestMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestServiceImpl implements TestService{

    @Resource
    TestMapper mapper;

    @Override
    public Student getStudent() {
        return mapper.getStudent();
    }

}

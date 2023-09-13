package Spring.PrincipleTest.entity;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class Student {

    String name;

    public Student() {
        System.out.println("我是构造方法");
    }

    @PostConstruct
    public void init() {
        System.out.println("我是初始化方法");
    }

    Test test;
    @Resource
    public void setMapper(Test test) {

        System.out.println("我是依赖注入");
        this.test = test;

    }

}

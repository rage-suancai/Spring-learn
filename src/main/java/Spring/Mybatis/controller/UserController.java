package Spring.Mybatis.controller;

import Spring.Mybatis.configuration.BeanConfiguration1;
import Spring.Mybatis.mapper.UserMapper;
import Spring.Mybatis.service.TestService;
import Spring.Mybatis.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class UserController {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration1.class);

    public static void main(String[] args) {

        //test1();
        //test2();
        test2();

    }

    static void test1() {

        SqlSessionTemplate template = context.getBean(SqlSessionTemplate.class);
        UserMapper mapper = template.getMapper(UserMapper.class);
        System.out.println(mapper.getUser());

    }

    static void test2() {
        log.info("🙂");
    }

    static void test3() {

        log.info("项目正在启动...");
        TestService service = context.getBean(TestService.class);
        service.test();

    }

}

package Spring.Mybatis.controller;

import Spring.Mybatis.config.BeanConfiguration1;
import Spring.Mybatis.mapper.UserMapper;
import Spring.Mybatis.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class UserController {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration1.class);

    public static void main(String[] args) {

        //test1();
        test2();

    }

    static void test1() {

        SqlSessionTemplate template = context.getBean(SqlSessionTemplate.class);
        UserMapper mapper = template.getMapper(UserMapper.class);
        System.out.println(mapper.getUser());

    }

    static void test2() {

        log.info("项目正在启动...");
        TestService service = context.getBean(TestService.class);
        service.test1();

    }

}

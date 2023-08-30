package Spring.SpringEL;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        ELTest el = (ELTest) context.getBean("MyEL");
        el.test();

    }

}

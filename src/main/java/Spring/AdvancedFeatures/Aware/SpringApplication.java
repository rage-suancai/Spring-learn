package Spring.AdvancedFeatures.Aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        AwareTest aware = (AwareTest) context.getBean("MyAware");

    }

}

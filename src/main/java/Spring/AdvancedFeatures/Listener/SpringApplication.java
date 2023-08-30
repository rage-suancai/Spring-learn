package Spring.AdvancedFeatures.Listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        ListenerTest listener = (ListenerTest) context.getBean("MyListener");

    }

}

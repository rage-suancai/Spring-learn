package Spring.SpringAOP.service;

import Spring.SpringAOP.bean3.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPApplication3 {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        test1();

    }

    static void test1() {



    }

}

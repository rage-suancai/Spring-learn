package Spring.SpringIoC.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication5 {

    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringIoC/application5.xml");

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        //System.out.println(context.getBean(Student.class));

        //context.getBean(StudentFactory.class).getStudent();

        System.out.println(context.getBean("&factory"));

    }

}

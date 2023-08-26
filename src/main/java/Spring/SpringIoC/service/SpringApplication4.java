package Spring.SpringIoC.service;

import Spring.SpringIoC.bean4.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication4 {

    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringIoC/application4.xml");

    public static void main(String[] args) {

        //test1();
        test2();

    }

    static void test1() {

        Student student = (Student) context.getBean("student");
        context.close();

    }
    static void test2() {

        //System.out.println(context.getBean("artStudent"));
        System.out.println(context.getBean("programStudent"));

    }

}

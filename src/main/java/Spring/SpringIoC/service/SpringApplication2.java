package Spring.SpringIoC.service;

import Spring.SpringIoC.bean2.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication2 {

    static ApplicationContext context = new ClassPathXmlApplicationContext("SpringIoC/application2.xml");

    public static void main(String[] args) {

        //test1();
        test2();

    }

    static void test1() {

        Student student = (Student) context.getBean("student");
        System.out.println(student);
        student.study();

    }
    static void test2() {

        Student student = (Student) context.getBean("student");
        System.out.println(student);

    }

}

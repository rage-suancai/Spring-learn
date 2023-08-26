package Spring.service;

import Spring.bean3.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication3 {

    static ApplicationContext context = new ClassPathXmlApplicationContext("application3.xml");

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        Student student = (Student) context.getBean("student");
        student.study();

    }

}

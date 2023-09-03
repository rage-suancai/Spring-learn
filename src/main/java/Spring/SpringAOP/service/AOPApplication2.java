package Spring.SpringAOP.service;

import Spring.SpringAOP.bean2.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPApplication2 {

    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringAOP/application2.xml");

    public static void main(String[] args) {

        test1();
        //test2();

    }

    static void test1() {

        Student student = context.getBean(Student.class);
        student.study("🤔");

    }

    static void test2() {



    }

}

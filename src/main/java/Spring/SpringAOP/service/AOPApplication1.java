package Spring.SpringAOP.service;

import Spring.SpringAOP.bean1.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPApplication1 {

    static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringAOP/application1.xml");

    public static void main(String[] args) {

        //test1();
        test2();

    }

    static void test1() {

        /*Student student = context.getBean(Student.class);
        student.study1();
        System.out.println(student.getClass());*/

        Student student = context.getBean(Student.class);
        student.study2("PHP");

    }

    static void test2() {

        Student student = context.getBean(Student.class);
        System.out.println("已报名: " + student.study3("Java"));

    }

}

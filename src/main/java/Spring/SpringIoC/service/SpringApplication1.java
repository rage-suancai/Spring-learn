package Spring.SpringIoC.service;

import Spring.SpringIoC.bean1.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication1 {

    static ApplicationContext context = new ClassPathXmlApplicationContext("SpringIoC/application1.xml");

    public static void main(String[] args) {

        //test1();
        //test2();
        test3();

    }

    static void test1() {

        Student student = context.getBean(Student.class);
        student.hello();

    }
    static void test2() {

        Student student = (Student) context.getBean("stu");
        student.hello();

    }
    static void test3() {

        Student student1 = (Student) context.getBean("stu");
        Student student2 = (Student) context.getBean("stu");
        System.out.println(student1 == student2);

    }

}

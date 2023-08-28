package Spring.SpringIoC.service;

import Spring.SpringIoC.bean6.SpringConfiguration;
import Spring.SpringIoC.bean6.Student;
import Spring.SpringIoC.bean6.Teacher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication6 {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        // test1();
        test2();

    }

    static void test1() {

        /*Student student = (Student) context.getBean("student");
        context.close();*/

    }

    static void test2() {

        Student student = (Student) context.getBean("student");
        System.out.println(context.getBean(Teacher.class));
        System.out.println(student);

    }

}

package Spring.SpringAOP.service;

import Spring.SpringAOP.bean3.SpringConfiguration;
import Spring.SpringAOP.bean3.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPApplication3 {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        test1();

    }

    static void test1() {

        Student student = context.getBean(Student.class);
        //student.study1();
        student.study2("😎");

    }

}

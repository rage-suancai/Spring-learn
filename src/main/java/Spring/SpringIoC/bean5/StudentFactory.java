package Spring.SpringIoC.bean5;

import org.springframework.beans.factory.FactoryBean;

public class StudentFactory implements FactoryBean<Student> {

    /*public static Student getStudent() {
        System.out.println("欢迎光临电子厂"); return new Student();
    }*/

    public Student getStudent() {
        System.out.println("欢迎光临电子厂"); return new Student();
    }

    @Override
    public Student getObject() throws Exception {
        return getStudent();
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

}

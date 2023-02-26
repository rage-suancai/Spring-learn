package Spring.springFoundation6.entity;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author YXS
 * @PackageName: Spring.springFoundation6.entity
 * @ClassName: StudentFactory
 * @Desription:
 * @date 2023/2/25 15:05
 */
public class Student6Factory implements FactoryBean<Student6> {

    public static Student6 getStudent1() {

        System.out.println("欢迎光临电子厂1");
        return new Student6();

    }

    public Student6 getStudent2() {

        System.out.println("欢迎光临电子厂2");
        return new Student6();

    }

    @Override
    public Student6 getObject() throws Exception {
        return getStudent1();
    }

    @Override
    public Class<?> getObjectType() {
        return Student6.class;
    }

}

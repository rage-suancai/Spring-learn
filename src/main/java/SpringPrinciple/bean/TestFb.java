package SpringPrinciple.bean;

import SpringPrinciple.entity.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author YXS
 * @PackageName: SpringPrinciple.bean
 * @ClassName: TestFb
 * @Desription:
 * @date 2023/3/8 23:15
 */
public class TestFb implements FactoryBean<Student> {

    @Override
    public Student getObject() throws Exception {

        System.out.println("获取了学生");
        return new Student();

    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

}

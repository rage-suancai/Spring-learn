package Spring.springFoundation7.entity;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springFoundation7.entity
 * @ClassName: StudentFACTORY
 * @Desription:
 * @date 2023/2/27 1:04
 */
@Component
public class Student7Factory implements FactoryBean<Student7> {

    @Override
    public Student7 getObject() throws Exception {
        return new Student7();
    }

    @Override
    public Class<?> getObjectType() {
        return Student7.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}

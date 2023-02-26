package Spring.springFoundation7.entity;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springFoundation7.entity
 * @ClassName: Student
 * @Desription:
 * @date 2023/2/26 0:57
 */
@ToString
@Component
public class Student7 {

    /*static {
        System.out.println("Fuck World");
    }*/

    /*eacher teacher;
    public Student7(Teacher teacher) {
        this.teacher = teacher;
    }*/

    @Autowired
    @Qualifier("b")
    Teacher teacher;

    @PostConstruct
    public void init() {
        System.out.println("我是初始化方法");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("我是销毁方法");
    }

}

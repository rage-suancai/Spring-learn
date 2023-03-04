package Spring.springEL1.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.SpringEL1.entity
 * @ClassName: Student1
 * @Desription:
 * @date 2023/2/27 16:18
 */
@Component
public class Student1 {

    private String name;

    /*@Value(value = "${student1.name}")
    String name;*/

    /*public Student1(@Value("${student1.name}")String name) {
        this.name = name;
    }*/

    public Student1(@Value("nb") String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("我的名字是: " + name);
    }

}

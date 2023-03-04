package Spring.springEL2.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.SpringEL2.entity
 * @ClassName: Student2
 * @Desription:
 * @date 2023/2/27 18:36
 */
@Component
public class Student2 {

    private String name;

    /*public Student2(@Value("${student2.name}") String name) {
        this.name = name;
    }*/

    public Student2(@Value("nb") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

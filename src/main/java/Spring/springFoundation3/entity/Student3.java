package Spring.springFoundation3.entity;

import lombok.ToString;

import java.util.List;

/**
 * @author YXS
 * @PackageName: Spring.spring3.entity
 * @ClassName: Student
 * @Desription:
 * @date 2023/2/24 10:30
 */
@ToString
public class Student3 {

    //String name;
    //private final Teacher teacher;
    private List<String> list;

    /*public Student3(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*public Student3(String name) {

        System.out.println("我是一号构造方法");
        this.name = name;

    }*/

    /*public Student3(int age) {

        System.out.println("我是二号构造方法");
        this.name = String.valueOf(age);

    }*/

    public void setList(List<String> list) {
        this.list = list;
    }

    /*public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*public void study() {
        teacher.teach();
    }*/

}

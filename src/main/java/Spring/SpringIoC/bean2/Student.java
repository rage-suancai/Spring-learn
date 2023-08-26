package Spring.SpringIoC.bean2;

import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class Student {

    private String name;
    private Teacher teacher;
    List<String> list;
    Map<String, Double> map;

    /*public void setName(String name) {
        this.name = name;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*public Student(Teacher teacher, String name) {

        System.out.println("我是一号");
        this.teacher = teacher;
        this.name = name;

    }
    public Student(Teacher teacher) {

        System.out.println("我是二号");
        this.teacher = teacher;

    }*/

    /*public void setList(List<String> list) {
        this.list = list;
    }*/

    public void setMap(Map<String, Double> map) {
        this.map = map;
    }

    public void study() {
        teacher.teach();
    }

}

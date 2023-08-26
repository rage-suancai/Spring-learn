package Spring.bean3;

import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
public class Student {

    Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /*public void setArt(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setProgram(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*public Student(Teacher teacher) {
        this.teacher = teacher;
    }*/

    public void study() {
        teacher.teach();
    }

}

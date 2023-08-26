package Spring.SpringIoC.bean3;

import lombok.ToString;

@ToString
public class Student {

    private Teacher teacher;

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

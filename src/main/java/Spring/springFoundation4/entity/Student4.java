package Spring.springFoundation4.entity;

/**
 * @author YXS
 * @PackageName: Spring.spring4.entity
 * @ClassName: Student
 * @Desription:
 * @date 2023/2/24 16:08
 */
public class Student4 {

    private Teacher teacher;

    /*public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*public void setProgram(Teacher teacher) {
        this.teacher = teacher;
    }*/

    public Student4(Teacher teacher) {
        this.teacher = teacher;
    }

    public void study() {
        teacher.teach();
    }

}

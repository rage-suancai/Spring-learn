package Spring.SpringIoC.bean6;

import org.springframework.context.annotation.*;

@Configuration
public class SpringConfiguration {

    /*@Bean("student")
    public Student student() {
        return new Student();
    }*/

    /*@Bean(name = "student", initMethod = "init", destroyMethod = "destroy", autowireCandidate = false)
    public Student student() {
        return new Student();
    }*/

    /*@Bean
    @Lazy(true)
    @Scope("singleton")
    //@DependsOn("teacher")
    public Student student() {
        return new Student();
    }*/

    /*@Bean
    public Teacher teacher() {
        return new ProgramTeacher();
    }
    @Bean("student")
    public Student student(Teacher teacher) {

        Student student = new Student();
        student.setTeacher(teacher);
        return student;

    }*/

    @Bean
    public Teacher teacher() {
        return new ProgramTeacher();
    }
    @Bean("student")
    public Student student() {

        Student student = new Student();
        return student;

    }

    /*@Bean(name = "a")
    public Teacher teacherA() {
        return new ProgramTeacher();
    }
    @Bean(name = "b")
    public Teacher teacherB() {
        return new ProgramTeacher();
    }
    @Bean("student")
    public Student student() {

        Student student = new Student();
        return student;

    }*/

}

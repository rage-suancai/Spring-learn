package Spring.springFoundation7.config;

import Spring.springFoundation7.entity.ArtTeacher;
import Spring.springFoundation7.entity.ProgramTeacher;
import Spring.springFoundation7.entity.Student7;
import Spring.springFoundation7.entity.Teacher;
import org.springframework.context.annotation.*;

import javax.xml.crypto.Data;

/**
 * @author YXS
 * @PackageName: Spring.springFoundation7
 * @ClassName: MainConfiguration
 * @Desription:
 * @date 2023/2/26 0:43
 */
@Configuration @ComponentScan("Spring.springFoundation7.entity")
public class MainConfiguration {

    /*@Bean("student7")
    public Student7 student7() {
        return new Student7();
    }*/

    /*@Bean(name = "student7", initMethod = "", destroyMethod = "", autowireCandidate = false)
    public Student7 student7() {
        return new Student7();
    }*/

    /*@Bean @Lazy
    @Scope("prototype") @DependsOn("teacher")
    public Student7 student7() {
        return new Student7();
    }*/

    /*@Bean
    public Teacher teacher() {
        return new ProgramTeacher();
    }
    @Bean
    public Student7 student7(Teacher teacher) {
        return new Student7(teacher);
    }*/

    /*@Bean
    public Teacher teacher() {
        return new ProgramTeacher();
    }
    @Bean
    public Student7 student7() {
        return new Student7();
    }*/

    /*@Bean("a")
    public Teacher teacherA() {
        return new ProgramTeacher();
    }
    @Bean("b")
    public Teacher teacherB() {
        return new ArtTeacher();
    }
    @Bean
    public Student7 student7() {
        return new Student7();
    }*/



}

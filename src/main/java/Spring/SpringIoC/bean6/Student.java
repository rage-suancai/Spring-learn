package Spring.SpringIoC.bean6;

import jakarta.annotation.Resource;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@ToString
public class Student {

    /*Student() {
        System.out.println("😋");
    }*/

    /*public void init() {
        System.out.println("我是初始化方法");
    }
    public void destroy() {
        System.out.println("我是销毁方法");
    }*/

    /*private Teacher teacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }*/

    /*@Autowired
    @Qualifier("a")
    private Teacher teacher;*/

    @Resource
    private Teacher teacher;

}

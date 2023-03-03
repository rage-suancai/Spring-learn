package Spring.springAOP3.entity;

import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springAOP3.entity
 * @ClassName: Student3
 * @Desription:
 * @date 2023/3/3 9:14
 */
@Component
public class Student3 {

    public void study1() {
        System.out.println("我是学习方法");
    }

    public void study2(String str) {
        System.out.println("我正在学习" + str);
    }

    public String study3(String str) {

        System.out.println("我正在学习" + str);
        return "yxsnb";

    }

    public String study4(String str) {

        if (str.equals("Rust")) System.out.println("我的梦想是学习Rust");
        System.out.println("我就要学Rust 不要修改我的梦想"); str = "Rust";
        return str;

    }

}

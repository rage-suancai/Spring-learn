package Spring.springAOP1.entity;

/**
 * @author YXS
 * @PackageName: Spring.springAOP1.entity
 * @ClassName: study
 * @Desription:
 * @date 2023/3/2 15:25
 */
public class Student1 {

    public void study1() {
        System.out.println("室友还在打游戏 我狠狠的学Java 太爽了");
    }

   public void study2(String str) {
       System.out.println("都别学习Java了 快去卷" + str);
   }

    public String study3(String str) {

        if (str.equals("Rust"))
            System.out.println("我的梦想是学习Rust");
        else
            System.out.println("我就要学Rust 不要修改我的梦想"); str = "Rust";
        return str;

    }

}

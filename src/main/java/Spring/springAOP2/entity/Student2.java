package Spring.springAOP2.entity;

/**
 * @author YXS
 * @PackageName: Spring.springAOP2.entity
 * @ClassName: Sutdnet2
 * @Desription:
 * @date 2023/3/3 0:13
 */
public class Student2 {

    public void study1() {
        System.out.println("我是学习方法");
    }

    public String study2() {

        System.out.println();
        return "我看到你们了...";

    }

}

package Spring.SpringAOP.bean1;

public class Student {

    public void study1() {
        System.out.println("室友还在打游戏 我狠狠的学Java 太爽了");
    }

    public void study2(String str) {
        System.out.println("都别学Java了 根本找不到工作 快去卷" + str);
    }

    public String study3(String str) {

        if (str.equals("Java")) System.out.println("我的梦想是学Java");
        System.out.println("我就要学Java, 不要修改我的梦想"); str = "Java";
        return str;

    }

}

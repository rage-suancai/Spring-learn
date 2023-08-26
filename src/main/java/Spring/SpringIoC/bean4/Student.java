package Spring.SpringIoC.bean4;

import lombok.ToString;

@ToString
public class Student {

    public void init() {
        System.out.println("我是对象初始化时要做的事情");
    }
    public void destroy() {
        System.out.println("我是对象销毁时要做的事情");
    }

}

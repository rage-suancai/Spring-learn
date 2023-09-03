package Spring.SpringAOP.bean2;

public class Student {

    public void study(String str) {
        System.out.println("Hello AOP" + str);
    }

    public void task(Runnable runnable) {
        runnable.run();
    }

}

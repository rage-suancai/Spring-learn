package Spring.SpringAOP.bean1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class StudentAOP {

    public void afterStudy1() {
        System.out.println("为什么毕业了他们都继承家产 我还倒给他们打工 我努力的意义在哪里...");
    }

    public void afterStudy2(JoinPoint point) {
        System.out.println("学什么" + point.getArgs()[0] + " Rust天下第一");
    }

    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("方法开始之前");
        //Object value = joinPoint.proceed();
        String arg = joinPoint.getArgs()[0] + "伞兵一号";
        Object value = joinPoint.proceed(new Object[]{arg});
        System.out.println("方法执行完成 结果为: " + value); return value;

    }

    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("我是她的家长 他不能学Java 必须学Rust 这是为他好");
        Object value = joinPoint.proceed(new Object[]{"Rust"});
        if (value.equals("Java")) System.out.println("听话 学Rust以后进大厂"); value = "Rust";
        return value;

    }

}

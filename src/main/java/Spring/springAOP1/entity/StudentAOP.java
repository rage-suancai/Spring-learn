package Spring.springAOP1.entity;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author YXS
 * @PackageName: Spring.springAOP1.entity
 * @ClassName: StudentAOP
 * @Desription:
 * @date 2023/3/2 15:34
 */
public class StudentAOP {

    public void afterStudy1() {
        System.out.println("为什么毕业了他们都继承家产 我们还倒给他们打工 我们努力的意义在哪里...");
    }

    public void afterStudy2(JoinPoint point) {
        System.out.println("学什么" + point.getArgs()[0] + " Rust天下第一");
    }

    public Object around(ProceedingJoinPoint joinPoint) {

        System.out.println("我是他的家长 他不能学Rust 必须学Java 这是为他好");
        try {
            Object value = joinPoint.proceed(new Object[]{"Java"});
            if (value.equals("Rust")) System.out.println("听话 学Java以后进大厂"); value = "Java";
            return value;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}

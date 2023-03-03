package Spring.springAOP3.entity;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springAOP3.entity
 * @ClassName: StudentAOP
 * @Desription:
 * @date 2023/3/3 9:24
 */
@Aspect
@Component
public class StudentAOP {

    @Before("execution(* Spring.springAOP3.entity.Student3.study1())")
    public void before1() {
        System.out.println("我是之前执行的内容");
    }

    /*@Before("execution(* Spring.springAOP3.entity.Student3.study2(String))")
    public void before2(JoinPoint point) {

        System.out.println("我是执行之前内容");
        System.out.println("参数列表: " + Arrays.toString(point.getArgs()));

    }*/
    @Before(value = "execution(* Spring.springAOP3.entity.Student3.study2(..)) && args(str)", argNames="str")
    public void before2(String str) {

        System.out.println("我是执行之前内容");
        System.out.println(str);

    }

    @AfterReturning(value = "execution(* Spring.springAOP3.entity.Student3.study3(..))", argNames="returnVal", returning="returnVal")
    public void afterReturn(Object returnVal) {
        System.out.println("返回值是: " + returnVal);
    }

    @Around("execution(* Spring.springAOP3.entity.Student3.study4(String))")
    public Object around(ProceedingJoinPoint joinPoint) {

        System.out.println("我是孩子他家长 他不能学Rust 必须学Java 这是为他好");
        try {
            Object value = joinPoint.proceed(new Object[]{"Java"});
            if (value.equals("Rust")) System.out.println("听话 学好Java进大厂"); value = "Java";
            return value;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}

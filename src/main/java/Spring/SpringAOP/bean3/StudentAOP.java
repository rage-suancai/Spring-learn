package Spring.SpringAOP.bean3;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAOP {

    @After("execution(* Spring.SpringAOP.bean3.Student.study1())")
    public void afterStudy1() {
        System.out.println("我是后置");
    }

    /*@After("execution(* Spring.SpringAOP.bean3.Student.study2(..))")
    public void afterStudy2(JoinPoint joinPoint) {

        System.out.println(joinPoint.getArgs()[0]);
        System.out.println("我是后置");

    }*/

    /*@After("execution(* Spring.SpringAOP.bean3.Student.study2(..)) && args(str)")
    public void afterStudy3(String str) {
        System.out.println(str);
    }*/

    @Around(value = "execution(* Spring.SpringAOP.bean3.Student.study2(..))")
    public Object aroundStudy4(ProceedingJoinPoint point) throws Throwable {

        System.out.println("方法执行之前");
        Object val = point.proceed();
        System.out.println("方法执行之后"); return val;

    }

}

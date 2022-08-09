package Spring.spring7.Aop;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;

@Aspect
@Component
public class AopTest {

    /*@Before("execution(* Spring.spring7.Bean.Student.say(..))")
    public void before(JoinPoint joinPoint){
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("我是方法执行之前要做的事情");
    }*/

    /*@AfterReturning(value = "execution(* Spring.spring7.Bean.Student.say(..))", returning = "val")
    public void after(Object val){
        System.out.println("我是方法执行之后要做的事情" + val);
    }*/

    @Around("execution(* Spring.spring7.Bean.Student.say(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("方法执行之前(环绕)");
        Object val = point.proceed();
        System.out.println("方法执行之后(环绕)");
        return val;
    }

}

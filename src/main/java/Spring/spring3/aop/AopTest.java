package Spring.spring3.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

@Log
public class AopTest {

    public void after(JoinPoint point){
        System.out.println(Arrays.toString(point.getArgs())); // 获取传入方法的实参
        log.info("我是方法执行之后的日志");
        System.out.println(point.getThis()); // 获取执行方法的对象
    }

    public void before(){
        log.info("我是方法执行之前的日志");
    }

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        /*System.out.println("方法执行前");
        Object value = joinPoint.proceed();
        System.out.println("方法执行完成 结果为: " + value);
        return value;*/

        System.out.println("方法执行前");
        String text = joinPoint.getArgs()[0] + "伞兵一号";
        Object value = joinPoint.proceed(new Object[]{text});
        System.out.println("方法执行完成 结果为: " + value);
        return value;
    }

}

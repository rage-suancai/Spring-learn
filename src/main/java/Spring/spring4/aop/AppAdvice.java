package Spring.spring4.aop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AppAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        /*System.out.println("方法名称为: " + method.getName());
        System.out.println("方法参数有: " + Arrays.toString(args));
        System.out.println("方法执行的对象为: " + target);*/
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法名称为: " + method.getName());
        System.out.println("方法参数有: " + Arrays.toString(args));
        System.out.println("方法执行的对象为: " + target);
        System.out.println("方法返回之值为: " + returnValue);
    }
}

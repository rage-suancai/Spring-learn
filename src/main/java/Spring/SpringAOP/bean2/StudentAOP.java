package Spring.SpringAOP.bean2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class StudentAOP implements MethodInterceptor {

    /*@Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("我是前置方法");
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("我是方法返回之后");
    }*/

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        System.out.println("执行之前");
        Object value = invocation.proceed();
        System.out.println("执行之后");
        return value + "增强";

    }

}

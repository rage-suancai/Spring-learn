package Spring.springAOP2.entity;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author YXS
 * @PackageName: Spring.springAOP2.entity
 * @ClassName: StudentAop
 * @Desription:
 * @date 2023/3/3 0:12
 */
public class StudentAOP implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object value = invocation.proceed();
        return value + " 你已经被强化了 快送";

    }

}

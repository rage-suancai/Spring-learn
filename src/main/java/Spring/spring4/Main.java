package Spring.spring4;

import Spring.spring4.Bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用接口实现AOP
 * 前面我们介绍了如何使用xml配置一个AOP操作 这节课我们来看看如何使用Advice实现AOP
 *
 * 它与我们之前学习的动态代理更接近一些 比如在方法开始执行之前或是执行后会去调用我们实现的接口 首先我们需要将一个类实现Advice接口
 * 只有实现此接口 才可以被通知 比如我们这里使用MethodBeforeAdvice表示是一个在方法执行之前的动作:
 *                  public class AppAdvice implements MethodBeforeAdvice {
 *                      @Override
 *                      public void before(Method method, Object[] args, Object target) throws Throwable {
 *                          System.out.println("方法名称为: " + method.getName());
 *                          System.out.println("方法参数有: " + Arrays.toString(args));
 *                          System.out.println("方法执行的对象为: " + target);
 *                      }
 *                  }
 *
 *                  <aop:config>
 *                      <aop:pointcut id="stu" expression="execution(* Spring.spring4.Bean.Student.say(String))"/>
 *                      <aop:advisor advice-ref="aopTest" pointcut-ref="stu"/>
 *                  </aop:config>
 * 我们发现 方法中包含了很多的参数 其中args代表的是方法执行前得到的实参列表 还有target表示执行此方法的实例对象
 * 运行之后 效果和之前是一样的 但是在这里我们就可以快速获取到更多信息
 *
 * 除了此接口 还有其他的接口比如AfterReturningAdvice 就需要实现一个方法执行之后的操作:
 *                  @Override
 *                  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
 *                      System.out.println("我是方法返回之后");
 *                  }
 *
 * 其实 我们之前学习的操作正好对应了AOP领域中的特性术语:
 *      > 通知(Advice) AOP框架中的增强处理 通知描述了切面何时执行以及如何执行增强处理 也就是我们上面编写的方法实现
 *      > 连接点(join point) 连接点表示执行过程中能够插入切面的一个点 这个点可以是方法的调用 异常的抛出 实际上就是我们在方法执行前或是执行后需要做的内容
 *      > 切点(PointCut) 可以插入增强处理的连接点 可以是方法执行之前也可以方法执行之后 还可以是抛出异常之类的
 *      > 切面(Aspect) 切面是通知和切点的结合 我们之前在xml中定义的就是切面 包括很多信息
 *      > 引入(Introduction) 引入允许我们向现有的类添加新的方法或者属性
 *      > 织入(Weaving) 将增强处理添加到目标对象中 并创建一个被增强的对象 我们之前都是将我们的增强处理添加到目标对象 也就是织入
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringTest.xml");

        Student student = context.getBean(Student.class);
        student.say("瑞克!!!");
    }

}

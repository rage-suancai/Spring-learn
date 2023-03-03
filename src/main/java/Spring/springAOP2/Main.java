package Spring.springAOP2;

import Spring.springAOP2.entity.Student2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用接口实现AOP
 * 前面我们介绍了如何使用xml配置一个AOP操作 这章节我们来看看如何使用Advice实现AOP
 *
 * 它与我们之前学习的动态带代理更接近一些 比如在方法开始执行之前或是执行之后会去调用我们实现的接口 首先我们需要将一个类实现Advice接口
 * 只有实现此接口 才可以被通知 比如我们这里使用MethodBeforeAdvice表示是一个在方法执行之前的动作:
 *
 *                  public class StudentAOP implements MethodBeforeAdvice {
 *
 *                      @Override
 *                      public void before(Method method, Object[] args, Object target) throws Throwable {
 *                          System.out.println("通过Advice实现的AOP");
 *                      }
 *
 *                  }
 *
 * 我们发现 方法中包括了很多的参数 其中args代表的是方法执行前得到的实参列表 还有target表示执行此方法的实例对象
 * 运行之后 效果和之前是一样的但是在这里我们就可以快速获取更多信息 还是以简单的study方法为例:
 *
 *                  public class Student2 {
 *
 *                      public void study1() {
 *                          System.out.println("我是学习方法");
 *                      }
 *
 *                  }
 *
 *                  <bean name="student2" class="Spring.springAOP2.entity.Student2"/>
 *                  <bean name="studentAOP" class="Spring.springAOP2.entity.StudentAOP"/>
 *                  <aop:config>
 *                      <aop:pointcut id="aft1" expression="execution(* Spring.springAOP2.entity.Student2.study1())"/>
 *                      <aop:advisor advice-ref="studentAOP" pointcut-ref="aft1"/>
 *                  </aop:config>
 *
 * 我们来测试一下吧:
 *
 *      https://smms.app/image/ofducpb2mLh9XHi
 *
 * 除了此接口以外 还有其他的接口 比如AfterReturningAdvice就需要实现一个方法执行之后的操作:
 *
 *                  public class StudentAOP implements MethodBeforeAdvice {
 *
 *                      @Override
 *                      public void before(Method method, Object[] args, Object target) throws Throwable {
 *                          System.out.println();
 *                      }
 *
 *                      @Override
 *                      public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
 *                          System.out.println("我是方法执行之后的结果 方法返回值为: " + returnValue);
 *                      }
 *
 *                  }
 *
 * 因为使用的是接口 就非常方便 直接写一起 配置文件都不需要改了:
 *
 *      https://smms.app/image/DUZzqaBSiJKNv8j
 *
 * 我们也可以使用MethodInterceptor(同样也是Advice的子接口) 进行更加环绕那样的自定义的增强 它起来就像真的代理一样 例子如下:
 *
 *                  public class Student2 {
 *
 *                      public String study2() {
 *
 *                          System.out.println("我是学习方法");
 *                          return "yxsnb";
 *
 *                      }
 *
 *                  }
 *
 * 我们来看看结果吧 使用起来还是挺简单的:
 *
 *      https://smms.app/image/ARcUW2mJrn7Y6f9
 */
public class Main {

    static ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationAOP.xml");
    static Student2 student2 = context.getBean(Student2.class);

    static void test() {

        // student2.study1();
        System.out.println(student2.study2());

    }

    public static void main(String[] args) {

        test();

    }

}

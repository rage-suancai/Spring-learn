package Spring.springAOP3;

import Spring.springAOP3.config.MainConfiguration;
import Spring.springAOP3.entity.Student3;
import org.aspectj.lang.JoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用注解实现AOP
 * 接着我们来看看如何使用注解实现AOP操作 现在变回我们之前的注解开发 首先我们需要在主类添加@EnableAspectJAutoProxy注解 开启AOP注解支持:
 *
 *                  @EnableAspectJAutoProxy
 *                  @ComponentScan("Spring.springAOP3.entity1")
 *                  @Configuration
 *                  public class MainConfiguration {
 *
 *                  }
 *
 * 还是熟悉的玩法 类上直接添加@Component快速注册Bean:
 *
 *                  @Component
 *                  public class Student3 {
 *
 *                      public void study1() {
 *                          System.out.println("我是学习方法");
 *                      }
 *
 *                  }
 *
 * 接着我们需要在定义AOP增强操作的类上添加@Aspect注解和@Component将其注册为Bean即可 就像我们之前在配置文件中也要将其注册为Bean那样:
 *
 *                  @Aspect
 *                  @Component
 *                  public class StudentAOP {
 *
 *                  }
 *
 * 接着 我们可以在里面编写增强方法 并将此方法添加到一个切点中 比如我们希望在Student的study方法执行之前执行我们的before方法:
 *
 *                  public void before1() {
 *                      System.out.println("我是之前执行的内容");
 *                  }
 *
 * 那么只需要添加@Before注解即可:
 *
 *                  @Before("execution(* Spring.springAOP3.entity.Student3.study1())")
 *                  public void before1() {
 *                      System.out.println("我是之前执行的内容");
 *                  }
 *
 * 这样 这个方法就会在指定方法执行之前执行了 是不是感觉比XML配置方便多了 我们来来测试一下:
 *
 *                  public static void main(String[] args) {
 *
 *                      static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
 *                      static Student3 student3 = context.getBean(Student3.class);
 *                      student3.study1();
 *
 *                  }
 *
 *      https://smms.app/image/KpiXcdNt7BglYQh
 *
 * 同样的 我们可以为其添加JoinPoint参数来获取切入点信息 使用方法跟之前一样:
 *
 *                  @Before("execution(* Spring.springAOP3.entity.Student3.study2(String))")
 *                  public void before2(JoinPoint point) {
 *
 *                      System.out.println("我是之前执行的内容");
 *                      System.out.println("参数列表: " + Arrays.toString(point.getArgs()));
 *
 *                  }
 *
 * 为了更方便 我们还可以直接将参数放入 比如:
 *
 *                  public void study2(String str) {
 *                      System.out.println("我正在学习" + str);
 *                  }
 *
 * 使用命名绑定模式 可以快速得到原方法的参数:
 *
 *                  @Before(value = "execution(* Spring.springAOP3.entity.Student3.study2(..)) && args(str)", argNames="str")
 *                  public void before2(String str) {
 *
 *                      // 命名绑定模式就是根据下面的方法参数列表进行匹配
 *                      // 这里args指明参数 注意需要跟原方法保持一致 然后在argNames中指明
 *                      System.out.println(str);
 *                      System.out.println("我是执行之前内容");
 *
 *                  }
 *
 * 除了@Before 还有很多可以直接使用的注解 比如@AfterReturning, @AfterThrowing等 比如@AfterReturning:
 *
 *                  public String study3() {
 *
 *                      System.out.println("我是学习方法");
 *                      return "yxsnb;"
 *
 *                  }
 *
 *                  // 使用returning指定接收方返回值的参数returnVal
 *                  @AfterReturning(value = "execution(* Spring.springAOP3.entity.Student3.study3(..))", argNames="returnVal", returning="returnVal")
 *                  public void afterReturn(Object returnVal) {
 *                      System.out.println("返回值是: " + returnVal);
 *                  }
 *
 * 同样的 环绕也可以直接通过注解声明:
 *
 *                  @Around("execurion(* )")
 *                  public Object around() {
 *
 *
 *
 *                  }
 *
 * 实际上 无论是使用注解或是XML配置 我们要做的流程都是一样的 在之后的学习中 我们还会遇到更多需要使用AOP的地方
 */
public class Main {

    static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
    static Student3 student3 = context.getBean(Student3.class);

    static void test() {

        // student3.study1();
        // student3.study2("Rust");
        // student3.study3("Rust");
        System.out.println("已报名" + student3.study4("Rust"));

    }

    public static void main(String[] args) {

        test();

    }

}

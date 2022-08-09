package Spring.spring7;

import Spring.spring7.Bean.Student;
import Spring.spring7.Bean.Teacher;
import Spring.spring7.config.MainConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;
import java.util.Date;

/**
 * Spring注解实现AOP操作
 * 了解了如何使用注解注册Bean之后 我们接着来看如何通过注解实现AOP操作 首先我们需要在主类添加@EnableAspectJAutoProxy注解 开启AOP注解支持:
 *                  @EnableAspectJAutoProxy
 *                  @Configuration
 *                  @ComponentScans({
 *                      @ComponentScan("Spring.spring7.Aop"),
 *                      @ComponentScan("Spring.spring7.Bean")
 *                  })
 *                  public class MainConfiguration {
 *
 *                  }
 * 接着我们只需要定义AOP增强操作的类上添加@Aspect注解和@Component将其注册为Bean即可 就像我们之前在配置文件中也要将其注册为Bean:
 *                  @Aspect
 *                  @Component
 *                  public class AopTest {
 *
 *                  }
 * 接着 我们直接在里面编写方法 并将此方法添加到一个切点中 比如我们希望在Student的test方法执行之前执行我们的方法:
 *                  public String say(String text){
 *                      System.out.println("我叫" + name + "今年" + age + "我的card属性为: " + card);
 *                      return text;
 *                  }
 * 只需要添加@Before注解即可:
 *                  @Before("execution(* Spring.spring7.Bean.Student.say(..))")
 *                  public void before(){
 *                      System.out.println("我是方法执行之前要做的事情");
 *                  }
 * 同样的 我们可以为其添加JoinPoint参数来获取切入点信息:
 *                  @Before("execution(* Spring.spring7.Bean.Student.say(..))")
 *                  public void before(JoinPoint joinPoint){
 *                      System.out.println(Arrays.toString(joinPoint.getArgs()));
 *                      System.out.println("我是方法执行之前要做的事情");
 *                  }
 * 我们也可以使用@AfterReturning注解来指定方法返回后的操作:
 *                  @AfterReturning(value = "execution(* Spring.spring7.Bean.Student.say(..))", returning = "val")
 *                  public void after(Object val){
 *                      System.out.println("我是方法执行之后要做的事情" + val);
 *                  }
 * 我们还可以指定returning属性 并将其作为方法某个参数的实参 同样的 环绕也可以直接通过注解声明:
 *                  @Around("execution(* Spring.spring7.Bean.Student.say(..))")
 *                  public Object around(ProceedingJoinPoint point) throws Throwable {
 *                      System.out.println("方法执行之前");
 *                      Object val = point.proceed();
 *                      System.out.println("方法执行之后");
 *                      return val;
 *                  }
 *
 * 其他注解配置
 * 配置文件可能不止一个 我们有可能会根据模块划分 定义多个配置文件 这个时候 可能会出现多个配置类 如果我们需要@Import
 * 注解来快速将某个类加入到容器中 比如我们现在创建一个新的配置文件 并将数据库Bean也搬过去:
 *                  public class MainConfiguration2 {
 *                      @Bean
 *                      public Teacher teacher(){
 *                          return new Teacher();
 *                      }
 *                  }
 *
 *                  @EnableAspectJAutoProxy
 *                  @Configuration
 *                  @ComponentScans({
 *                      @ComponentScan("Spring.spring7.Bean"),
 *                      @ComponentScan("Spring.spring7.Aop")
 *                  })
 *                  @Import(MainConfiguration2.class)
 *                  public class MainConfiguration {
 *
 *                  }
 *
 *                  Teacher teacher = context.getBean(Teacher.class);
 *                  System.out.println(teacher);
 * 注意另一个配置类并没有添加任何注解 实际上 相当于导入的类被强制注册为了一个Bean 到现在 我们一共了解了三种注册为Bean的方式 利用这种特性 我们还可以将其他类也强制注册为Bean:
 *                  @Import(MainConfiguration2.class, Date.class)
 *
 *                  System.out.println(context.getBean(Date.class));
 * 可以看到 日期直接作为一个Bean放入到IoC容器中了 并且时间永远都是被new的那个时间 也就是同一个对象(因为默认是单例模式)
 *
 * 通过@Import方式最主要为了实现的目标并不是创建Bean 而是为了方便一些框架的Registrar进行Bean定义 在讲解到Spring原理时 我们再来详细讨论 目前只做了解即可
 *
 * 这里 关于Spring框架的大致内容就聊的差不多了 其余内容 我们在后面继续讨论
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        /*Student student = context.getBean(Student.class);
        System.out.println(student.say("执行了方法"));*/

        /*Teacher teacher = context.getBean(Teacher.class);
        System.out.println(teacher);*/

        /*Connection connection = context.getBean(Connection.class);
        System.out.println(connection);*/

        /*System.out.println(context.getBean(Date.class));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(context.getBean(Date.class));*/

    }

}

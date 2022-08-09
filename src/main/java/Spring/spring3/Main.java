package Spring.spring3;

import Spring.spring3.Bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 面向切面AOP
 * 又是一个听起来很高大上的名词 AOP思想实际上就是: 在运行时 动态地将代码切入到指定方法 指定位置上的思想就是面向切面的编程
 * 也就是说 我们可以使用AOP来帮助我们在方法执行前执行之后 做一下额外的操作 实际上 就是代理
 *
 * 通过AOP我们可以在保证原有业务不变的情况下 添加额外的动作 比如我们的某些方法执行完成之后 需要打印日志 那么这个时候 我们就可以使用AOP来帮助我们完成
 * 它可以批量地为这些方法添加动作 可以说 它相当于将我们原有的方法 在不改变源代码的基础上进行增强处理
 *
 * 相当于我们的整个业务流程 被直接斩断 并在断掉的位置添加了一个额外的操作 在连接起来 也就是在一个切点位置插入内容 它的原理实际上就是通过动态代理机制实现的
 * 我们在javaWeb阶段已经给大家讲解过动态代理了 不过Spring层并不是使用的JDK提供的动态代理 而是使用的第三方库实现 它能够以父类的形式代理 而不是接口
 *
 * 使用SpringAOP
 * Spring是支持AOP编程的框架之一 (实际上它整合了AspectJ框架的一部分) 要使用AOP我们需要先导入一个依赖:
 *                  <dependency>
 *                      <groupId>org.springframework</groupId>
 *                      <artifactId>spring-aspects</artifactId>
 *                      <version>5.3.13</version>
 *                  </dependency>
 * 那么 如何使用AOP呢 首先我们要明确 要实现AOP操作 我们需要知道这些内容:
 *      1 需要切入的类 类的哪个方法需要被切入
 *      2 切入之后需要执行什么动作
 *      3 是在方法执行前切入还是方法执行后切入
 *      4 如何告诉Spring需要进行切入
 *
 * 那么我们依次来看 首先需要解决问题是 找到需要切入的类:
 *                  // 分别在test方法执行前后切入
 *                  public int test(String str){
 *                      System.out.println("我是一个测试方法:" + str);
 *                      return str.length();
 *                  }
 * 现在我们希望在test方法执行前后添加我们的额外执行的内容 接着 我们来看看如何为方法执行前和执行后添加切入动作
 * 比如在我们想在方法返回之后 在执行我们的动作 首先定义我们要执行的操作:
 *                  public class AopTest {
 *
 *                      public void after(){
 *                          log.info("我是方法执行之后的日志");
 *                      }
 *
 *                      public void before(){
 *                          log.info("我是方法执行之前的日志");
 *                      }
 *
 *                  }
 * 那么 现在如何告诉Spring我们需要在方法执行之前和之后插入其他逻辑呢 首先我们将要进行AOP操作的类注册为Bean:
 *                  <bean name="student" class="Spring.spring3.Bean.Student"/>
 *                  <bean name="aopTest" class="Spring.spring3.aop.AopTest"/>
 * 一个是Student类 还有一个就是包含我们要切入方法的AopTest类 注册为Bean后 他们就交给Spring进行管理 这样Spring才能帮助我们完成AOP操作
 *
 * 接着 我们需要告诉Spring 我们需要添加切入点 首先将顶部修改为 引入aop相关标签:
 *                  http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd"
 * 通过使用aop:config 来添加一个新的AOP配置:
 *                  <aop:config>
 *
 *                  </aop:config>
 * 首先第一行 我们需要告诉Spring 我们要切入的是哪一个类的哪个或是哪些方法:
 *                  <aop:config>
 *                      <aop:pointcut id="stu" expression="execution(* Spring.spring3.Bean.Student.say(String))"/>
 *                  </aop:config>
 * 其中 expression属性的execution填写格式如下:
 *                  修饰符 包名.类名.方法名称(方法参数)
 *      > 修饰符 public protected private 包括返回值类型 static等等(使用*代表任意修饰符)
 *      > 包名 如com.test(代表全部 比如com.代表com包下的全部包)
 *      > 类名 使用*也可以代表包下的所有类
 *      > 方法名称 可以使用*代表全部方法
 *      > 方法参数 填写对应的参数即可 比如(String, String) 也可以使用*来代表任意一个参数 使用。。代表所有参数
 *
 * 也可以使用其他属性来进行匹配 比如@annotation可以用于表示标记了哪些注解的方法被切入
 *
 * 接着 我们需要为此方法添加一个执行前动作和一个执行后动作:
 *                  <aop:aspect>
 *                      <aop:before method="before" pointcut-ref="test"/>
 *                      <aop:after-returning method="after" pointcut-ref="test"/>
 *                  </aop:aspect>
 * 这样 我们就完成了全部的配置 现在来实验一下吧:
 *                  Student student = context.getBean(Student.class);
 *                  student.say("马牛逼!!!");
 *
 * 我们发现 方法执行前后 分别调用了我们对应的方法 但是仅仅这样还是不能满足一些需求 在某些情况下 我们可以需求方法执行的一些参数 比如方法执行之后返回了什么 或是方法开始之前传入了什么参数等等
 *
 * 这个时候 我们可以为我们切入的方法添加一个参数 通过此参数就可以快速获取切点位置的一些信息:
 *                  // 执行之前的方法
 *                  public void after(JoinPoint point){
 *                      System.out.println(Arrays.toString(point.getArgs())); // 获取传入方法的实参
 *                      log.info("我是方法执行之后的日志");
 *                      System.out.println(point.getThis()); // 获取执行方法的对象
 *                  }
 * 通过添加JoinPoint作为形参 Spring会自动给我们一个实现类对象 这样我们就能获取方法的一些信息了
 *
 * 最后我们在来看环绕方法 环绕方法相当于完全代理了此方法 它完全将此方法包含在中间 需要我们手动调用才可以执行此方法 并且我们可以直接获取更多的参数:
 *                  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
 *                      System.out.println("方法执行前");
 *                      Object value = joinPoint.proceed();
 *                      System.out.println("方法执行完成 结果为: " +value);
 *                      return value;
 *                  }
 * 注意: 如果代理方法存在返回值 那么环绕方法也需要有一个返回值 通过proceed方法来执行代理的方法 也可以修改参数之后调用proceed(Object[]) 使用我们给定的参数再去执行:
 *                  System.out.println("方法执行前");
 *                  String text = joinPoint.getArgs()[0] + "伞兵一号";
 *                  Object value = joinPoint.proceed(new Object[]{text});
 *                  System.out.println("方法执行完成 结果为: " + value);
 *                  return value;
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringTest.xml");

        Student student = context.getBean(Student.class);
        //System.out.println(student.getClass());
        student.say("马牛逼!!!");
        //student.test();
    }

}

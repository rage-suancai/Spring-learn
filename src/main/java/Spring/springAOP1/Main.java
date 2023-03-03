package Spring.springAOP1;

import Spring.springAOP1.entity.Student1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用配置实现AOP
 * 在开始之前 我们先换回之前的XML配置模式 之后也会给大家讲解如何使用注解完成AOP操作
 * 注意这里我们还加入了一些新的AOP相关的约束进来 建议直接CV下面的:
 *
 *                  <?xml version="1.0" encoding="UTF-8"?>
 *                  <beans xmlns="http://www.springframework.org/schema/beans"
 *                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                         xmlns:aop="http://www.springframework.org/schema/aop"
 *                         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
 *                         http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
 *
 *                  </beans>
 *
 * Spring是支持AOP编程的框架之一(实际上它整合了AspectJ框架的一部分) 要使用AOP我们需要导入一个依赖:
 *
 *                  <dependency>
 *                      <groupId>org.springframework</groupId>
 *                      <artifactId>spring-aspects</artifactId>
 *                      <version>6.0.4</version>
 *                  </dependency>
 *
 * 那么 如何使用AOP呢? 首先我们要明确 要实现AOP操作 我们需要知道这些内容:
 *
 *      1. 需要切入的类 类的哪个方法需要被切入
 *      2. 切入之后需要执行什么动作
 *      3. 是在方法执行前切入还是在方法执行后切入
 *      4. 如何告诉Spring需要进行切入
 *
 * 比如现在我们希望对这个学生对象的study方法进行增强 在不修改源代码的情况下 增加一些额外的操作:
 *
 *                  public class Student {
 *
 *                      public void study() {
 *                          System.out,println("室友还在打游戏 我狠狠的学Java 太爽了");
 *                          // 现在我们希望在这个方法执行完之后 打印一些其他的内容 在不修改原有代码的情况下 该怎么做呢?
 *                      }
 *
 *                  }
 *
 *                  <bean class="Spring.springAOP1.entity.Student"/>
 *
 * 那么我们按照上面的流程 依次来看 首先需要解决的问题是 找到需要切入的类 很明显 就是这个Student类 我们要切入的是这个study方法
 *
 * 第二步 我们切入之后要做什么呢? 这里我们直接创建一个新的类 并将要执行的操作写成一个方法:
 *
 *                  public class StudentAOP {
 *
 *                      // 这个方法就是我们打算对其进行的增强操作
 *                      public void afterStudy() {
 *                          System.out.println("为什么毕业了他们都继承家产 我们还倒给他们打工 我们努力的意义在哪里...");
 *                      }
 *
 *                  }
 *
 * 注意这个类也得注册为Bean才可以:
 *
 *                  <bean name="studentAOP" class="Spring.springAOP1.entity.StudentAOP"/>
 *
 * 第三步 我们要明确这是在方法执行之前切入还是执行之后切入 很明显 按照上面的要求 我们需要执行之后进行切入操作呢? 这里我们需要在配置文件中进行AOP配置:
 *
 *                  <aop:config>
 *
 *                  </aop:config>
 *
 * 接着我们需要添加一个新的切点 首先填写ID 这个随便起都可以:
 *
 *                  <aop:pointcut id="test" expression=""/>
 *
 * 然后就是通过后面的expression表达式来选择我们需要切入的方法 这个表达式支持很多种方式进行选择 SpringAOP支持以下AspectJ切点指示器(PCD)用于表达式:
 *
 *      > execution: 用于匹配方法执行连接点 (这是使用Spring AOP时使用的主要点切割指示器)
 *      > within: 限制匹配到某些类型的连接点 (使用Spring AOP时在匹配类型中声明的方法的执行)
 *      > this: 限制与连接点匹配 (使用Spring AOP时方法的执行) 其中目标对象(正在代理的应用程序对象)是给定类型的实例
 *      > target: 限制匹配连接点 (使用Spring AOP时方法的执行) 其中目标对象(正在代理的应用程序对象)是给定类型的实例
 *      > args: 限制与连接点匹配 (使用Spring AOP时方法的执行) 其中参数是给定类型的实例
 *
 *      > @target: 限制匹配连接点 (使用Spring AOP时方法的执行) 其中执行对象的类具有给定类型的注释
 *      > @args: 限制匹配连接点 (使用Spring AOP时方法的执行) 其中传递的实际参数的运行时类型具有给定类型的注释
 *      > @within: 限制与具有给定注释的类型中的连接点匹配 (使用Spring AOP时在带有给定注释的类型中声明的方法的执行)
 *      > @annotation: 与连接点主体 (在Spring AOP中运行的方法) 具有给定注释的连接点匹配的限制
 *
 * 更多详细内容请查阅: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pointcuts-designators
 *
 * 其中 我们主要学习的execution填写格式 如下:
 *
 *                  修饰符 包名.类名.方法名称(方法参数)
 *
 *      > 修饰符: public protected private 包括返回值类型 static等等 (使用代表任意修饰符)
 *      > 包名: 如com.test (*代表全部 比如com.代表com包下的全部包)
 *      > 类名: 使用*也可以代表包下的所有类
 *      > 方法名称: 可以使用*代表全部方法
 *      > 方法参数: 填写对应的参数即可 比如(String, String) 也可以使用*来代表任意一个参数 使用..代表所有参数
 *
 * 也可以使用其他属性来进行匹配 比如@annotation可以用于表示标记了哪些注解的方法被切入 这里我们就只是简单的执行 所以说需要这样写就可以了:
 *
 *                  <aop:pointcut id="aft" expression="execution="(* Spring.springAOP1.entity.Student.study())"/>
 *
 * 这样 我们就指明了需要切入的方法 然后就是将我们的增强方法 我们在里面继续添加aop:aspect标签 并使用ref属性将其指向我们刚刚注册的AOP类Bean:
 *
 *                  <aop:config>
 *                      <aop:pointcut id="aft" expression="execution="(* Spring.springAOP1.entity.Student.study())"/>
 *                      <aop:aspect ref="studentAOP">
 *
 *                      </aop:aspect>
 *                  </aop:config>
 *
 * 接着就是添加后续动作了 当然 官方支持的有多种多样的 比如执行前 执行后 抛出异常后 方法返回后等等:
 *
 *      https://smms.app/image/uopJ9KyqMvQSwi4
 *
 * 其中around方法为环绕方法 自定义度会更高 我们会在稍后介绍 这里我们按照上面的要求 直接添加后续动作 注意: 需要指明生效的切点:
 *
 *                  <aop:aspect>
 *                      <!-- method就是我们的增强方法 pointcut-ref指向我们刚刚创建的切点 -->
 *                      <aop:after method="afterStudy" pointcut-ref="studentAOP"/>
 *                  </aop:aspect>
 *
 * 这样 我们就成功配置好了 配置正确会在旁边出现图标:
 *
 *      https://smms.app/image/hBaSmuovMzp5iIn
 *
 * 我们来试试看吧:
 *
 *                  public static void main() {
 *
 *                      ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationAOP.xml");
 *                      Student1 student1 = context.getBean(Student1.class);
 *                      student1.study();
 *
 *                  }
 *
 * 结果如下:
 *
 *      https://smms.app/image/JlvLe9rgQw2pbXo
 *
 * 可以看到在我们原本的方法执行完成之后 它还继续执行了我们的增强方法 这实际上就是动态代理做到的 实现在不修改原有代码的基础上
 * 对方法的调用进行各种增强 在之后的SpringMVC学习中 我们甚至可以使用它来快速配置访问日志打印
 *
 * 前面我们说了 AOP是基于动态代理实现的 所以说我们如果直接获取Bean的类型 会不会发现不是原本的类型了:
 *
 *                  Student1 student1 = context.getBean(Student1.class);
 *                  System.out.println(student1.getClass);
 *
 *                  class Spring.springAOP1.entity.Student1$$SpringCGLIB$$0
 *
 * 这里其实是Spring通过CGLib为我们生成的动态代理类 也就不难理解为什么调用方法会直接得到增强之后的结果了
 * 包括我们前面讲解Spring的异步任务调度时 为什么能够直接实现异步 其实就是利用了AOP机制实现的方法增强
 *
 * 虽然这些功能已经非常强大了 但是仅仅只能简单的切入还是不能满足一些需求 在某些情况下 我们可以需求方法执行的一些参数
 * 比如方法执行之后返回了什么 或是方法开始之前传入了什么参数等等 现在我们修改一下Student中study方法的参数:
 *
 *                  public class Student1() {
 *
 *                      public void study2(String str) { // 现在方法有个一String类型的参数
 *                          System.out.println("都别学Java了 快去卷" + str);
 *                      }
 *
 *                  }
 *
 * 我们希望在增强的方法中也能拿到这个参数 然后进行处理:
 *
 *                  public class StudentAOP {
 *
 *                      public void afterStudy2(String str) {
 *                          // 这个str参数我们该从哪里拿呢?
 *                          System.out.println("学什么" + str + " Rust天下第一");
 *                      }
 *
 *                  }
 *
 * 这个时候 我们可以为我们切入的方法添加一个JoinPoint参数 通过此参数就可以快速获取切点位置的一些信息:
 *
 *                  public void afterStudy2(JoinPoint point) { // JoinPoint实例会被自动传入
 *                      // 这里我们直接通过getArgs()返回的参数数组获取第一个参数
 *                      System.out.println("学什么" + point.getArgs()[0] + " Rust天下第一");
 *                  }
 *
 * 接着我们修改一下刚刚的AOP配置(因为方法参数有变动) 看看结果吧:
 *
 *                  <aop:pointcut id="aft2" expression="execution(* Spring.springAOP1.entity.Student1.study2(String))"/>
 *
 * 现在我们来来测试一下:
 *
 *                  public static void main(String[] args) {
 *
 *                      ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationAOP.xml");
 *                      Student1 student1 = context.getBean(Student1.class);
 *                      student1.study2("PHP");
 *
 *                  }
 *
 *      https://smms.app/image/NrZA49JvpgEyL2O
 *
 * 是不是感觉大部分功能都可以通过AOP来完成了?
 *
 * 我们接着来看自定义度更高的环绕方法 现在我们希望在方法执行前和执行后都加入各种各样的动作
 * 如果还是一个一个切点写 有点太慢了 能不能直接写一起呢 此时我们就可以使用环绕方法
 *
 * 环绕方法相当于完全代理了此方法 它完全将此方法包含在中间 需要我们手动调用才可以执行此方法 并且我们可以直接获取更多的参数:
 *
 *                  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
 *
 *                     System.out.println("方法开始之前");
 *                     Object value = joinPoint.proceed(); // 调用process方法来执行被代理的原方法 如果有返回值 可以使用value接收
 *                     System.out.println("方法执行完成 结果为: " + value);
 *                     return value;
 *
 *                  }
 *
 * 注意 如果代理方法存在返回值 那么环绕方法也需要有一个返回值 通过proceed方法来执行代理的方法 也可以修改参数之后调用proceed(Object[]) 使用我们给定的参数再去执行:
 *
 *                  public Object around(ProceedingJoinPoint joinPoint) throw Throwable {
 *
 *                      System.out.println("方法开始之前");
 *                      String arg = joinPoint.getArgs()[0] + "伞兵一号";
 *                      Object value = joinPoint.proceed(new Object[]{arg});
 *                      System.out.println("方法执行完成 结果为: " + value);
 *                      return value;
 *
 *                  }
 *
 * 这里我们还是study方法为例 现在我们希望在调用前修改这个方法传入的参数值 改成我们自己的 然后在调用之后对返回值结果也进行处理:
 *
 *                  public String study3(String str) {
 *
 *                      if (str equals("Rust"))
 *                          System.out.println("我的梦想是学习Rust");
 *                      else {
 *                          System.out.println("我就要学Rust 不要修改我的梦想");
 *                          str = "Rust";
 *                      }
 *                      return str;
 *
 *                  }
 *
 * 现在我们编写一个环绕方法 对其进行全方面处理:
 *
 *                  public Object around(ProceedingJoinPoint joinPoint) {
 *
 *                      System.out.println("我是他的家长 他不能学Rust 必须学Java 这是为他好");
 *                      try {
 *                          Object value = joinPoint.proceed(new Object[]{"Java"});
 *                          if (value.equals("Rust")) {
 *                              System.out.println("听话 学Java以后进大厂");
 *                              value = "Java";
 *                          }
 *                          return value;
 *                      } catch (Throwable e) {
 *                          throw new RuntimeException(e);
 *                      }
 *
 *                  }
 *
 * 同样的 因为方法变动了 现在我们去修改一下我们的AOP配置:
 *
 *                  <aop:pointcut id="aft3" expression="execution(* Spring.springAOP1.entity.Student1.study3(String))"/>
 *                  <aop:aspect ref="studentAOP">
 *                      <aop:around method="around" pointcut-ref="aft3"/>
 *                  </aop:aspect>
 *
 *      https://smms.app/image/FPwQjRvsDgTnoWx
 *
 * 细心的小伙伴可能会发现 环绕方法的图标是全包的 跟我们之前的图标不太一样 现在我们来试试看吧:
 *
 *                  public static void main(String[] args) {
 *
 *                      ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationAOP.xml");
 *                      Student1 student1 = context.getBean(Student1.class);
 *                      System.out.println("已报名: " + bean.study("Java"));
 *
 *                  }
 *
 *      https://smms.app/image/pGl7n8qboe4tuJf
 *
 * 这样 我们就实现了环绕方法 通过合理利用AOP带来的便捷 可以使得我们的代码更加清爽和优美 这里介绍一下AOP领域中的特性术语 防止自己下来看不懂文章:
 *
 *      > 通知(Advice): AOP框架中的增强处理 通知描述了切面何时执行以及执行增强处理 也就是我们上面编写的方法实现
 *      > 连接点(JoinPoint): 连接点表示应用执行过程中能够插入切面的一个点 这个点可以是方法的调用 异常的抛出 实际上就是我们在方法执行前或是执行后需要做的内容
 *      > 切点(PointCut): 可以插入增强处理的连接点 可以是方法执行之前也可以方法执行之后 还可以是抛出异常之类的
 *      > 切面(Aspect): 切面是通知和切点的结合 我们之前在xml中定义的就是切面 包括很多信息
 *      > 引入(Introduction): 引入允许我们向现有的类添加新的方法或是属性
 *      > 织入(Weaving): 将增强处理添加到目标对象中 并创建一个被增强的对象 我们之前都是在将我们的增强处理添加到目标对象 也就是织入(这名字挺有文艺范的)
 */
public class Main {
    static ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationAOP.xml");
    static Student1 student1 = context.getBean(Student1.class);

    static void test1() {

        //student1.study1();
        //student1.study2("PHP");
        System.out.println("已报名: " + student1.study3("Java"));

    }

    static void test2() {

        System.out.println(student1.getClass());

    }

    public static void main(String[] args) {

        test1();
        //test2();

    }

}

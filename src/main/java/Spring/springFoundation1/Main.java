package Spring.springFoundation1;

import Spring.springFoundation1.entity.Student1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 第一个Spring项目
 * 首先一定要明确 使用Spring首要目的是为了使得软件进行解耦 而不是为了去简化代码 通过它 就可以更好的对我们的Beam进行管理 这一部分我们来体验一下Spring的基本使用
 *
 * Spring并不是一个独立的框架 它实际上包含了很多的模块:
 *
 *      https://img-blog.csdnimg.cn/img_convert/37d9e48e300d9993a46ce53a1291e24f.png
 *
 * 而我们首先要去学习的就是Core Container 也就是核心容器模块 只有了解了Spring的核心技术 我们才能真正认识这个框架为我们带来的便捷之处
 *
 *                  <dependency>
 *                      <groupId>org.springframework</groupId>
 *                      <artifactId>spring-context</artifactId>
 *                      <version>6.0.4</version>
 *                  </dependency>
 *
 * 注意: 与旧版本教程不同的是 Spring6要求你使用的Java版本为17及以上 包括后面我们在学习SpringMVC时 要求Tomcat版本必须为10以上 这个依赖中包含了如下依赖:
 *
 *      https://img-blog.csdnimg.cn/img_convert/67ca44aefda4c42f66147b474941de53.png
 *
 * 这里出现的都是Spring核心相关的内容 如Beans Core Context SpEL以及非常关键的AOP框架 在本章中 我们都会进行讲解
 *
 *      如果在使用Spring框架的过程中出现如下警告:
 *      12月 17, 2022 3:26:26 下午 org.springframework.core.LocalVariableTableParameterNameDiscoverer inspectClass
 *      警告: Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: XXXX
 *
 *      这是因为LocalVariableTableParameterNameDiscoverer在Spring6.0.1版本已经被标记为过时 并且即将移除 请在Maven配置文件中为编译插件添加-parameters编译参数:
 *
 *      <build>
 *          <pluginManagement>
 *                  <plugins>
 *                      <plugin>
 *                          <artifactId>maven-compiler-plugin</artifactId>
 *                          <version>3.10.1</version>
 *                          <configuration>
 *                              <compilerArgs>
 *                                  <arg>-parameters</arg>
 *                              </compilerArgs>
 *                          </configuration>
 *                      </plugin>
 *                  </plugins>
 *          </pluginManagement>
 *      </build>
 *
 *      没有此问题请无视这部分
 *
 * 这里我们就来尝试编写一个最简单的Spring项目 我们在前面已经讲过了 Spring会给我们提供IoC容器用于管理Bean
 * 但是我们得先为这个容器编写一个配置文件 我们可以通过配置文件告诉容器需要管理哪些Bean已经Bean的属性 依赖关系等等
 *
 * 首先我们需要在resource中创建一个Spring配置文件(在resource中创建的文件 会在编译时被一起放到类路径下) 名为application.xml 直接右键点击即可创建:
 *
 *                  <?xml version="1.0" encoding="UTF-8"?>
 *                  <beans xmlns="http://www.springframework.org/schema/beans"
 *                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                         xsi:schemaLocation="http://www.springframework.org/schema/beans
 *                          https://www.springframework.org/schema/beans/spring-beans.xsd">
 *
 *                  </beans>
 *
 * 此时IDEA会提示我们没有为此文件配置应用程序上下文 这里我们只需要指定成当前项目就行了 当然配置这个只是为了代码提示和依赖关系快速查看 如果不进行配置也不会影响什么 程序依然可以正常运行:
 *
 *      https://img-blog.csdnimg.cn/img_convert/7be44bfc46b04a52592ba484642c486b.png
 *
 * 这里我们直接按照默认配置点确定就行了:
 *
 *      https://img-blog.csdnimg.cn/img_convert/2742d87823719194ad83a434fc737ad8.png
 *
 * Spring为我们提供了一个IoC容器 用于去存放我们需要使用的对象 我们可以将对象交给IoC容器进行管理 当我们需要使用对象时 就可以向IoC容器去索要 并由它来决定给我们哪一个对象
 * 而我们如果需要使用Spring为我们提供的IoC容器 那么就需要创建一个应用程序上下文 它代表的就是IoC容器 它会负责实例化 配置和组装Bean:
 *
 *                  public static void main(String[] args) {
 *                      // ApplicationContext是应用程序上下文的顶层接口 它有很多种实现
 *                      // 因为这里使用的是XML配置文件 所以说我们就使用ClassPathXmlApplicationContext
 *                      ApplicationContext context = new ClassPathXmlApplicationContext();
 *                  }
 *
 * 比如现在我们要让IoC容器帮助我们管理一个Student对象(Bean) 当我们需要这个对象时在申请 那么就需要这样 首先先将Student类定义出来:
 *
 *                  public class Student {
 *
 *                      public void hello() {
 *                          System.out.println("Fuck World");
 *                      }
 *
 *                  }
 *
 * 既然现在要让别人帮忙管理对象 那么就不能再由我们自己去new这个对象了 而是编写对应的配置 我们打开刚刚创建的application.xml文件进行编辑 添加:
 *
 *                  <bean name="student" class="com.test.bean.Student"/>
 *
 * 这里我们就在配置文件中编写好了对应Bean的信息 之后容器就会根据这里的配置进行处理了
 *
 * 现在 这个对象不需要我们再去创建了 而是由IoC容器自动进行创建并提供 我们可以直接从上下文中获取到它为我们创建的对象:
 *
 *                  public static void main() {
 *
 *                      ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");
 *                      Student student = (Student) context.getBean("student"); // 使用getBean方法来获取对应的对象(Bean)
 *                      student.hello();
 *
 *                  }
 *
 * 实际上 这里得到的Student对象是由Spring通过反射机制帮助我们创建的 初学者会非常疑惑 为什么要这样来创建对象
 * 我们直接new一个它不香吗? 为什么要交给IoC容器管理呢? 在后面的学习中 我们在慢慢进行体会
 *
 *      https://img-blog.csdnimg.cn/img_convert/e250bbabbe3c06f1a2680787a580e436.png
 */
public class Main {

    static void test1() {

        ApplicationContext context1 = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");
        Student1 student = (Student1) context1.getBean("student1");
        //System.out.println(student);
        student.hello();

    }

    public static void main(String[] args) {

        test1();

    }

}

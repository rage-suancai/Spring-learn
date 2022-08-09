package Spring.spring5;

import Spring.spring5.Bean.Student;
import Spring.spring5.config.MainConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring使用注解开发
 * 前面我们已经了解了IoC容器和AOP实现 但是我们发现 要使用这些功能 我们就不得不编写大量的配置 这是非常浪费时间和精力的 并且我们还只是演示了几个小的例子
 * 如果是像之前一样去编写一个完整的Web应用出现 那么生成的配置可能会非常多 能否有一种更加高效的方法能够省去配置呢 当然是注解了
 *
 * 所以说 第一步先把你的xml配置文件实删了吧 现在我们全部使用注解进行开发(哈哈 是不是感觉XML配置白学了 其实没有)
 *
 * 注解实现配置文件
 * 那么 现在既然不使用XML文件了 那通过注解的方式就只能以实体类的形式进行配置了 我们在要作为配置的类添加 @Configuration注解 我们这里创建一个新的类MainConfiguration:
 *                  @Configuration
 *                  public class MainConfiguration {
 *                      // 没有配置任何Bean
 *                  }
 * 你可以直接把它等价于:
 *                  <?xml version="1.0" encoding="UTF-8"?>
 *                  <beans xmlns="http://www.springframework.org/schema/beans"
 *                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
 *                         xsi:schemaLocation="http://www.springframework.org/schema/beans
 *                         http://www.springframework.org/schema/beans/spring-beans.xsd>
 *                         <!-- 没有配置任何Bean -->
 *                  </beans>
 * 那么我们来看看 如何配置Bean 之前我们是直接在配置文件在编写Bean的一些信息 现在在配置类中 我们只需要编写一个方法 并返回我们要创建的Bean的对象即可 并在其上方添加@Bean注解:
 *                  @Bean
 *                  public Card card(){
 *                      return new Card();
 *                  }
 * 这样 等价于:
 *                  <?xml version="1.0" encoding="UTF-8"?>
 *                  <beans xmlns="http://www.springframework.org/schema/beans"
 *                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
 *                         xsi:schemaLocation="http://www.springframework.org/schema/beans
 *                         http://www.springframework.org/schema/beans/spring-beans.xsd>
 *
 *                         <bean class="Spring.spring.bean.Card"></bean>
 *
 *                  </bean
 * 我们还可以继续添加@Scope注解来指定作用域 这里我们就用原型模式:
 *                  @Bean
 *                  @Scope("prototype")
 *                  public Card card(){
 *                      return new Card();
 *                  }
 * 采用这种方式 我们就可以更加方便地控制一个Bean对象的创建过程 现在相当于这个对象时由我们创建好了 再交给Spring进行后续处理 我们可以在对象创建时做很多额外的操作 包括一些属性值的配置等
 *
 * 既然现在我们已经创建好了配置类 接着我们就可以在主方法中加载此配置类 并创建一个基于配置类的容器:
 *                  public static void main(String[] args) {
 *                      // 使用AnnotationConfigApplicationContext来实现注解配置
 *                      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class); // 这里需要告诉Spring哪个类作为配置类
 *
 *                      Card card = context.getBean(Card.class); // 容器用法和之前一样
 *                      System.out.println(card);
 *                  }
 * 在配置的过程中 我们可以点击IDEA底部的Spring标签 打开后可以对当前向容器中注册的Bean进行集中查看 并且会标注Bean之间的依赖关系
 * 我们发现 Bean的默认名称实际上就是首字母小写的方法名称 我们也可以手动指定:
 *                  @Bean("yxsnb")
 *                  @Scope("prototype")
 *                  public Card card(){
 *                      return new Card();
 *                  }
 * 除了像原来一样在配置文件中创建Bean以为 我们还可以在类上添加@Component注解来将一个类进行注册(现在最常用的方式) 不过要实现这样的方式
 * 我们需要添加一个自动扫描 来告诉Spring需要在哪些包中查找我们提供@Component声明的Bean
 *
 * 只需要在配置类上添加一个@ComponentScan注解即可 如果要添加多个包进行扫描 可以使用@ComponentScans来批量添加 这里我们演示将bean包下的所有类进行扫描:
 *                  @Configuration
 *                  @ComponentScan("Spring.spring5.Bean")
 *                  public class MainConfiguration {
 *
 *                  }
 * 现在删除类中的Bean定义 我们在Student类的上面添加@Component注解 来表示此类型需要作为Bean交给容器进行管理:
 *                  @Component
 *                  public class Student {
 *                      int age;
 *                      String name;
 *                      Card card;
 *
 *                      public void setName(String name) {
 *                          this.name = name;
 *                      }
 *                  }
 * 与@Component同样效果的还有@Controller @Service和@Repository 但是现在暂时不提 讲到SpringMVC时在来探讨
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        Student student = context.getBean(Student.class);
        System.out.println(student);
    }

}

package Spring.springFoundation5;

import Spring.springFoundation5.entity.ArtStudent;
import Spring.springFoundation5.entity.ProgramStudent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 生命周期与继承
 * 除了修改构造方法 我们也可以为Bean指定初始化方法和销毁方法 以便在对象创建和被销毁时执行一些其他的任务:
 *
 *                  public void init() {
 *                      System.out.println("我是对象初始化时要做的事情");
 *                  }
 *
 *                  public void destroy() {
 *                      System.out.println("");
 *                  }
 *
 * 我们可以通过init-method和destroy-method来指定:
 *
 *                  <bean name="student" class="Spring.springFoundation5.entity.Student5" init-method="" />
 *
 * 那么什么时候是初始化 上面时候又是销毁呢?
 *
 *                  // 当容器创建时 默认情况下Bean都是单例的 那么都会在一开始就加载好 对象构造完成后 会执行init-method
 *                  ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");
 *                  // 我们可以调用close方法关闭容器 此时容器内存放的Bean也会被一起销毁 会执行destroy-method
 *                  context.close();
 *
 * 所以说 最后的结果为:
 *
 *      https://img-blog.csdnimg.cn/img_convert/a5135d452abaeae03639280482f65657.png
 *
 * 注意 如果Bean不是单例模式 而是采用的原型模式 那么就只会在获取时才创建 并调用init-method 而对应的销毁方法不会被调用
 * (因此 对于原型模式下的Bean Spring无法顾及其完整生命周期 而在单例模式下 Spring能够从Bean对象的创建一直管理到对象的销毁) 官方文档原文如下:
 *
 *      In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype
 *      bean. The container instantiates, configures, and otherwise assembles a prototype object and
 *      hands it to the client, with no further record of that prototype instance. Thus, although
 *      initialization lifecycle callback methods are called on all objects regardless of scope, in the
 *      case of prototypes, configured destruction lifecycle callbacks are not called. The client code
 *      must clean up prototype-scoped objects and release expensive resources that the prototype
 *      beans hold. To get the Spring container to release resources held by prototype-scoped beans,
 *      try using a custom bean post-processor, which holds a reference to beans that need to be cleaned up.
 *
 * Bean之间也是具有继承关系的 只不过这里的继承并不是类的继承 而是属性的继承 比如:
 *
 *                  public class SportStudent {
 *                      private String name;
 *
 *                      public void setName(String name) {
 *                          this.name = name;
 *                      }
 *                  }
 *
 *                  public class ArtStudent {
 *                      private String name;
 *
 *                      public void setName(String name) {
 *                          this.name = name;
 *                      }
 *                  }
 *
 * 此时 我们先将ArtStudent注册一个Bean:
 *
 *                  <bean name="artStudent" class="Spring.springFoundation5.entity.ArtStudent">
 *                      <property name="name" value="李牛逼"/>
 *                  </bean>
 *
 * 这里我们会注入一个name的初始值 此时我们创建了一个SpringStudent的Bean 我们希望这个Bean的属性跟刚刚创建的Bean属性是一样的 那么我们可以写一个一模一样的:
 *
 *                  <bean name="programStudent" class="Spring.springFoundation5.entity.ProgramStudent">
 *                      <property name="name" value="李牛逼"/>
 *                  </bean>
 *
 * 但是如果属性太多的话 是不是写起来有点麻烦? 这种情况 我们就可以配置Bean之间的继承关系了 我们可以让SportStudent这个Bean直接继承ArtStudent这个Bean配置的属性:
 *
 *                  <bean class="Spring.springFoundation5.entity.ProgramStudent" parent="artStudent"/>
 *
 * 这样 在ArtStudent Bean中配置的属性 会直接继承给ProgramStudent Bean(注意 所有配置的属性在子Bean中必须要存在并且可以进行注入 否则会出现错误)
 * 当然 如果子类中某些属性比较特殊 也可以在继承的基础上单独配置:
 *
 *                  <bean name="art" class="Spring.springFoundation5.entity.ArtStudent">
 *                      <property name="name" value="李牛逼"/>
 *                  </bean>
 *                  <bean name="program" class="Spring.springFoundation5.entity.ProgramStudent" parent="art">
 *                      <property name="id" value="2"/>
 *                  </bean>
 *
 * 如果我们只是希望某一个Bean及作为一个配置模板供其他Bean继承使用 那么我们可以将其配置为abstract 这样 容器就不会创建这个Bean的对象了:
 *
 *                  <bean name="program" class="Spring.springFoundation5.entity.ProgramStudent" parent="true">
 *                      <property name="name" value="李牛逼"/>
 *                  </bean>
 *                  <bean class="Spring.springFoundation5.entity.ProgramStudent" parent="program"/>
 *
 * 注意 一旦声明为抽象Bean 那么就无法通过容器获取到其他实例化对象了
 *
 *      https://img-blog.csdnimg.cn/img_convert/079cf5788cecf5fb319b27bee4498fd6.png
 *
 * 不过Bean的继承使用频率不是很高 掌握就行
 *
 * 这里最后再提一下 我们前面已经学习了各种各样的Bean配置属性 如果我们希望整个上下文中所有的Bean都采用某种配置 我们可以再最外层的beans标签中进行默认配置
 *
 *      https://img-blog.csdnimg.cn/img_convert/64b53c7b5776fd474109a8c22932edf5.png
 *
 * 这样 即使Bean没有配置某项属性 但是只要再最外层编写了默认配置 那么同样会生效 除非Bean自己进行配置覆盖掉默认配置
 */
public class Main {

    static void test1() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");

        /*context.close();
        Student5 student5 = context.getBean(Student5.class);
        System.out.println(student5);*/

        System.out.println(context.getBean(ProgramStudent.class));
        System.out.println(context.getBean(ArtStudent.class));

    }

    public static void main(String[] args) {

        test1();

    }

}

package Spring.spring1;

import Spring.spring1.Bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用IoC容器
 * 首先一定要明确 使用Spring首要目的是为了使得软件项目进行解耦 而不是为了去简化代码 Spring并不是一个独立的框架 它实际上包含了很多的模块
 * 而我们首先要去学习的就是Core Container 也就是核心容器模块 Spring是一个非入侵式的框架 就像一个工具库一样 因此 我们只需要直接导入其依赖就可以使用了
 *
 * 第一个Spring项目
 * 我们创建一个新的Maven项目 并导入Spring框架的依赖 Spring框架的坐标:
 *                  <dependency>
 *                      <groupId>org.springframework</groupId>
 *                      <artifactId>spring-context</artifactId>
 *                      <version>5.3.13</version>
 *                  </dependency>
 * 接着在resource中创建一个Spring配置文件 命名为SpringTest.xml 直接点击即可创建:
 *                  <?xml version="1.0" encoding="UTF-8"?>
 *                  <beans xmlns="http://www.springframework.org/schema/beans"
 *                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                      xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
 *
 *                  </beans>
 * 最后 在主方法中编写:
 *                  ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringTest.xml");
 *
 * 这样 一个最基本的Spring项目就创建完成了 接着我们来看看如何向IoC容器中注册javaBean 首先创建一个Student类:
 *                  // 注意 这里还用不到值注入 只需要包含成员属性即可 不用Getter/Setter
 *                  public class Student {
 *                      String name;
 *                      int age;
 *                  }
 * 最后在配置文件中添加这个bean:
 *                  <bean name="student" class="Spring.spring1.Bean.Student"/>
 * 现在 这个对象不需要我们再去生成了 而是由IoC容器来提供:
 *                  Student student = (Student) context.getBean("student");
 *                  System.out.println(student);
 *
 * 实际上 这里得到的Student对象是由Spring通过反射机制帮助我们创建的 初学者会非常疑惑 为什么要这样来创建对象 我们直接new一个不香吗
 * 为什么要交给IoC容器管理呢 在后面的学习中 我们再慢慢的进行体会
 *
 * 将javaBean交给IoC容器管理
 * 通过前面的例子 我们发现只要将我们创建好的javaBean通过配置文件编写 即可将其交给IoC容器进行管理 那么我们来看看 一个javaBean的详细配置:
 *                  <bean name="student" class="Spring.spring1.Bean.Student"/>
 * 其中name属性(也可以是id属性) 全局唯一 不可出现重复的名称 我们发现 之前其实就是通过Bean的名称来向IoC容器索要对应的对象 也可以通过其他方式获取
 *
 * 我们现在为主方法中连续获取两个对象:
 *                  Student student1 = (Student) context.getBean("student");
 *                  Student student2 = (Student) context.getBean("student");
 *                  System.out.println(student1);
 *                  System.out.println(student2);
 * 我们发现两次获取到的实际上是同一个对象 也就是说 默认情况下 通过IoC容器进行管理的javaBean是单例模式的
 * 无论怎么获取始终为那一个对象 那么如何进行修改呢 只需要修改其作用域即可 添加scope属性:
 *                  <bean name="student" class="Spring.spring1.Bean.Student" scope="prototype"/>
 *
 * 通过将其设定为prototype(原型模式) 来使得每次都会创建一个新的对象 我们接着来观察一下 这两种模式下Bean的生命周期 我们给构造方法添加一个输出:
 *                  public Student(){
 *                      System.out.println("我被构造了");
 *                  }
 * 接着我们在main方法中打上断点来查看对象分别是在什么时候被构造的
 *
 * 我们发现 当Bean的作用域为单例模式 那么会在一开始就被创建 而处于原型模式下 只有在获取时才会被创造 也就是说 单例模式下 Bean会被IoC容器存储
 * 只要容器没有被销毁 那么此对象将一直存在 而原型模式才是相当于直接new了一个对象 并不会被保存
 *
 * 我们还可以通过配置文件 告诉创建一个对象需要执行此初始化方法 以及销毁一个对象的方法:
 *                  public void init(){
 *                      System.out.println("我是初始化方法");
 *                  }
 *
 *                  public void destroy(){
 *                      System.out.println("我是销毁方法");
 *                  }
 *
 *                  public Student(){
 *                      System.out.println("我被构造了");
 *                  }
 *
 *                  Student student = (Student) context.getBean("student");
 *                  System.out.println(student);
 *                  context.close(); // 手动销毁容器
 * 最后在XML文件中编写配置:
 *                  <bean name="student" class="Spring.spring1.Bean.Student" init-method="init" destroy-method="destroy"/>
 * 接下来测试一下即可
 *
 * 我们还可以手动指定Bean的加载顺序 若某个Bean需要保证一定在另一个Bean加载之前加载 那么就可以使用depend-on属性:
 *                  <bean name="student" class="Spring.spring1.Bean.Student" depends-on="card"/>
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringTest.xml");

        /*Student student1 = (Student) context.getBean("student");
        Student student2 = (Student) context.getBean("student");
        System.out.println(student1);
        System.out.println(student2);*/

        /*Student student = (Student) context.getBean("student");
        System.out.println(student);
        context.close();*/
    }

}

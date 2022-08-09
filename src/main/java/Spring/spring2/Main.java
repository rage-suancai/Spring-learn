package Spring.spring2;

import Spring.spring2.Bean.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 依赖注入DI
 * 现在我们已经了解了如何注册和使用一个Bean 那么 如何向Bean的成员属性进行赋值呢 也就是说 IoC在创建对象时 需要将我们预先给定的属性注入到对象中
 * 非常简单 我们可以使用property标签来实现 但是一定注意: 此属性必须存在一个set方法 否则无法赋值:
 *                  <bean name="student" class="Spring.spring2.Bean.Student">
 *                      <property name="name" value="普京"/>
 *                  </bean>
 *
 *                  public class Student {
 *                      String name;
 *                      int age;
 *
 *                      public void setName(String name) {
 *                          this.name = name;
 *                      }
 *
 *                      public void say(){
 *                          System.out.println("我是: " + name);
 *                      }
 *                  }
 * 最后测试是否能够成功将属性注入到我们的对象中:
 *                  Student student = (Student) context.getBean("student");
 *                  student.say();
 *
 * 那么 如果成员属性是一个非基本类型非Spring的对象类型 我们该如何注入呢
 *                  public class Card{
 *
 *                  }
 *
 *                  public class Student {
 *                      String name;
 *                      int age;
 *                      Card card;
 *
 *                      public void setName(String name) {
 *                          this.name = name;
 *                      }
 *
 *                      public void setAge(int age) {
 *                          this.age = age;
 *                      }
 *
 *                      public void setCard(Card card) {
 *                          this.card = card;
 *                      }
 *                  }
 * 我们只需要将对应的类型也注册为Bean即可 然后直接使用ref属性来进行引用:
 *                  <bean name="student" class="Spring.spring2.Bean.Student" scope="prototype">
 *                      <property name="name" value="普京"/>
 *                      <property name="age" value="50"/>
 *                      <property name="card" ref="card"/>
 *                  </bean>
 *                  <bean name="card" class="Spring.spring2.Bean.Card"/>
 *
 * 那么 集合如何实现注入呢 我们需要在property内部进行编写:
 *                  List<Double> list;
 *                  public void setList(List<Double> list) {
 *                      this.list = list;
 *                  }
 *
 *                  <property name="list">
 *                      <list>
 *                          <value type="double">123.8</value>
 *                          <value type="double">148.4</value>
 *                          <value type="double">178.68</value>
 *                      </list>
 *                  </property>
 * 现在 我们就可以直接以一个数组的方式将属性注入 注意如果是List类型的话 我们也可以使用array数组 同样的如果是一个Map类型 我们也可以使用entry来注入:
 *                  Map<String, Double> map;
 *                  public void setMap(Map<String, Double> map) {
 *                      this.map = map;
 *                  }
 *
 *                  <map>
 *                      <entry key="语文" value="256.88"/>
 *                      <entry key="英语" value="867.32"/>
 *                      <entry key="数学" value="243.12"/>
 *                  </map>
 *
 * 我们还可以使用自动装配来实现属性值的注入:
 *                  <bean name="card" class="Spring.spring2.Bean.Card"/>
 *                  <bean name="student" class="Spring.spring2.Bean.Student" autowire="byType">
 * 自动装配会根据set方法中需要的类型 自动在容器中查找是否存在对应类型或是以及对应名称以及对应方法的Bean
 * 比如我们上面指定的为byType 那么其中的card属性就会被自动注入类型为Card的Bean
 *
 * 我们已经了解了如何使用set方法创建对象 那么能否不使用默认的无参构造方法 而是指定一个有参构造进行对象的创建呢 我们可以指定构造方法:
 *                  <bean name="student" class="Spring.spring2.Bean.Student" autowire="byType">
 *                      <constructor-arg name="name" value="普京"/>
 *                      <constructor-arg name="age" value="50"/>
 *                  </bean>
 *
 *                  public Student(String name, int age){
 *                      this.age = age;
 *                      this.name = name;
 *                  }
 * 通过手动指定构造方法参数 我们就可以直接告诉容器使用哪一个构造方法来创建对象
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringTest.xml");

        /*Student student1 = (Student) context.getBean("student");
        System.out.println(student1);
        Student student2 = (Student) context.getBean("student");
        System.out.println(student2);*/

        Student student = (Student) context.getBean("student");
        System.out.println(student);

    }

}

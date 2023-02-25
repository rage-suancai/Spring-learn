package Spring.springFoundation4;

import Spring.springFoundation4.entity.Student4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 自动装配
 * 在之前 如果我们需要使用依赖注入的话 我们需要对property参数进行配置:
 *
 *                  <bean name="teacher" class="Spring.spring3.entity.ProgramTeacher"/>
 *                  <bean name="student4" class="Spring.Spring4.entity.Student4">
 *                      <property name="teacher" ref="teacher"/>
 *                  </bean>
 *
 * 但是有些时候为了方便 我们也可以开启自动装配 自动装配就算让IoC容器自己去寻找需要填入的值
 * 我们只需要将test方法提供好就可以了 这里需要添加autowire属性:
 *
 *                  <bean name="teacher" class="Spring.spring4.entity.ProgramTeacher"/>
 *                  <bean name="student4" class="Spring.Spring4.entity.Student4" autowire="byType"/>
 *
 * autowire属性有两个值 一个是byName 还有一个是byType 顾名思义 一个是根据类型去寻找合适的Bean自动装配 还有一个是根据名字去找 这样我们不需要显示指定property了
 *
 *      https://img-blog.csdnimg.cn/img_convert/e87bed6e8478bb8d1e3354bed67b0550.png
 *
 * 此时set方法旁边会出现一个自动装配图标 效果和上面是一样的
 *
 * 对于使用构造方法完成的依赖注入 也支持自动装配 我们只需要将autowire修改为:
 *
 *                  <bean name="student4" class="Spring.Spring4.entity.Student4" autowire="constructor"/>
 *
 * 这样 我们只需要提供一个对应参数的构造方法就可以了(这种情况默认也是byType寻找的):
 *
 *      https://img-blog.csdnimg.cn/img_convert/f556bcf450f7c53169f364ededfdec75.png
 *
 * 这样同样可以完成自动注入:
 *
 *      https://img-blog.csdnimg.cn/img_convert/9b96562bc46dd527ca6bb7cae3f2a838.png
 *
 * 自动化的东西虽然省事 但是太过机械 有些时候 自动装配可能会遇到一些问题 比如出现了下面的情况:
 *
 *      https://img-blog.csdnimg.cn/img_convert/5950d23f553df293d3a9cf7d6759aa38.png
 *
 * 此时 由于autowire的规则为byType 存在两个候选Bean 但是我们其实希望ProgramTeacher这个Bean在任何情况下都不参与到自动装配中 此时我们就可以将它的自动装配候选关闭:
 *
 *                  <bean name="art" class="Spring.Spring4.entity.ArtTeacher" />
 *                  <bean name="program" class="Spring.Spring4.entity.ProgramTeacher" autowire-candidate="false"/>
 *                  <bean name="student4" class="Spring.Spring4.entity.Student4" />
 *
 * 当autowire-candidate设定false时 这个Bean将不再作为自动装配的候选Bean 此时自动装配候选就只剩下一个唯一的Bean了 报错消失 程序可以正常运行
 *
 * 除了这种方式 我们也可以设定primary属性 表示这个Bean作为主要的Bean 当出现歧义时 也会优先选择:
 *
 *                  <bean name="art" class="Spring.Spring4.entity.ArtTeacher"/>
 *                  <bean name="program" class="Spring.Spring4.entity.ProgramTeacher" primary="true"/>
 *                  <bean name="student4" class="Spring.Spring4.entity.ArtTeacher" autowire="byType"/>
 *
 * 这样写程序依然可以正常运行 并且选择的也是ArtTeacher(就是不知道为什么IDEA会上红标 BUG?)
 */
public class Main {

    static void test1() {

        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");

        Student4 student4 = (Student4) context.getBean("student4");
        student4.study();

    }

    public static void main(String[] args) {

        test1();

    }

}

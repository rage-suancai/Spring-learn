package Spring.springEL1;

import Spring.springEL1.config.MainConfiguration;
import Spring.springEL1.entity.Student1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 外部属性注入
 * 有些时候 我们甚至可以将一些外配置文件中的配置进行读取 并完成注入
 *
 * 我们需要创建以.properties结尾的配置文件 这种配置文件格式很简单 类似于Map 需要一个Key和一个Value
 * 中间使用等号进行连接 这里我们在resource目录下创建一个SpringEL.properties文件:
 *
 *                  test.name=只因
 *
 * 这样 Key就是test.name Value就是只因 我们可以通过一个注解直接读取到外部配置文件中对应属性值
 * 首先我们需要引入这个配置文件 我们可以在配置类上添加@PropertuSource注解:
 *
 *                  @Configuration
 *                  @ComponentScan(""Spring.SpringEL1.entity"")
 *                  @PropertySource("classpath:SpringEL.properties") // 注意 类路径下的文件名称需要在前面加上classpath:
 *                  public class MainConfiguration {
 *
 *                  }
 *
 * 接着 我们就可以开始快乐的使用了 我们可以使用@Value注解将外部配置文件中的值注入到任何我们想要的位置 就像我们之前使用@Resource自动注入一样:
 *
 *                  @Component
 *                  public class Student1 {
 *
 *                      @Value("${Student1.name}") // 这里需要在外层套上 ${}
 *                      private String name; // String会被自动赋值为配置文件中对应属性的值
 *
 *                      public void hello() {
 *                          System.out.println("我的名字是: " + name);
 *                      }
 *
 *                  }
 *
 * @Value 中的${...}表示占位符 它会读取外部配置文件的属性值装配到属性中 如果配置正确没问题的话 这里甚至还会直接显示对应配置项的值:
 *
 *      https://img-blog.csdnimg.cn/img_convert/4dc9c5796c7424b7f693953e757f2fb0.png
 *
 * 我们来测试一下吧:
 *
 *      https://img-blog.csdnimg.cn/img_convert/60ec3fe08626e9bd3755c65cefabcf4b.png
 *
 * 如果遇到乱码的情况 请将配置文件的编码格式切换成UTF-8(可以在IDEA设置中进行配置)
 * 然后在@PropertySource注解中添加属性encoding="UTF-8" 这样就正常了 当然 其实一般情况下也很少会在配置文件中用到中文
 *
 * 除了在字段上进行注入之外 我们也可以在需要注入的方法中使用:
 *
 *                  @Component
 *                  public class Student1 {
 *
 *                      private final String name;
 *
 *                      // 构造方法中的参数除了被自动注入之外 我们也可以选择使用@Value进行注入
 *                      public Student(@Value("${student1.name}) String name) {
 *                          this.name = name;
 *                      }
 *
 *                      public void hello() {
 *                          System.out.println("我的名字是: " + name);
 *                      }
 *
 *                  }
 *
 * 当然 如果我们只想简单的注入一个常量值 也可以直接填入固定值:
 *
 *                  private final String name;
 *
 *                  public Student1(@Value("nb") String name) {
 *                      this.name = name;
 *                  }
 *
 * 当然 @Value的功能还远远不止这些 配合SpringEL表达式 能够实现更加强大的功能
 */
public class Main {

    static void test1() {

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        Student1 student1 = context.getBean(Student1.class);
        student1.hello();

    }

    public static void main(String[] args) {

        test1();

    }

}

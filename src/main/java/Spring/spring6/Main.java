package Spring.spring6;

import Spring.spring6.Bean.Student;
import Spring.spring6.config.MainConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring注解进行自动注入
 * 现在我们就有两种方式注册一个Bean了 那么如何实现像之前一样的自动注入呢 比如我们将Card也注册为Bean 我们希望Spring自动将其注入到Student属性中:
 *                  @Component
 *                  public class Student {
 *                      int age;
 *                      String name;
 *                      Card card;
 *                  }
 * 因此 我们可以将此类型 也通过这种方式注册为一个Bean:
 *                  @Component
 *                  public class Card {
 *                  }
 * 现在 我们在需要注入的位置 添加一个@Resource注解来实现自动装配置:
 *                  @Component
 *                  public class Student {
 *                      int age;
 *                      String name;
 *
 *                      @Resource
 *                      Card card;
 *                  }
 * 这样的好处是 我们完全不需要创建任何的set方法 只需要添加这样的一个注解就可以了 Spring会跟之前配置文件的自动注入一样 在整个容器中进行查找
 * 并将对应的Bean实例对象注入到此属性中 当然 如果还是需要通过set方法 可以将注解添加到方法上:
 *                  @Resource
 *                  public void setCard(Card card) {
 *                      System.out.println("我是通过set方法注入的属性");
 *                      this.card = card;
 *                  }
 * 除了使用@Resource以为 我们还可以使用@Autowiredd (IDEA不推荐将其使用在字段上 会出现黄标 但是可以放在方法或是构造方法上) 它们的效果是一样的 但是它们存在区别 虽然它们都是自动装配
 *      > @Resource 默认ByName如果找不到则ByType 可以添加到set方法 字段上
 *      > @Autowired 默认是byType 可以添加在构造构造方法上 set方法 字段 方法参数上
 *
 * 并且 @Autowired可以配合@Qualifier使用 来指定一个名称的Bean进行注入:
 *                  @Autowired
 *                  @Qualifier("yxsnb")
 *                  public void setCard(Card card) {
 *                      System.out.println("我是通过set方法注入的属性");
 *                      this.card = card;
 *                  }
 *
 *                  @Bean("yxsnb")
 *                  public Card card(){
 *                      return new Card();
 *                  }
 * 如果Bean是在配置文件中进行定义的 我们还可以在方法的参数中使用@Autowired来进行自动注入:
 *                  @Configuration
 *                  @ComponentScan("Spring.spring6.Bean")
 *                  public class MainConfiguration {
 *                      @Bean
 *                      public Student student(@Autowired Card card){
 *                          Student student = new Student();
 *                          student.setCard(card);
 *                          return student;
 *                      }
 *                  }
 *
 * 我们还可以通过@PostContruct注解来添加构造后执行的方法 它等价于之前讲解的init-method:
 *                  @PostConstruct
 *                  public void init(){
 *                      System.out.println("我是初始化方法");
 *                  }
 *
 * 注意它们的顺序: Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct 同样的 如果需要销毁方法 也可以使用@PreDestroy注解 这里就不演示了
 *
 * 这样 两种通过注解进行Bean声明的方式就讲解完毕了 那么什么时候该用什么方式去声明呢
 *      > 如果要注册为Bean的类是由其他框架提供 我们无法修改其源代码 那么我们就使用第一种方式进行配置
 *      > 如果要注册为Bean的类是我们自己编写的 我们就可以直接在类上添加注解 并在配置中添加扫描
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        Student student = context.getBean(Student.class);
        System.out.println(student);
    }

}

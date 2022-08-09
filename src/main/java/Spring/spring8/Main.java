package Spring.spring8;

/**
 * Spring集成JUnit测试
 * 既然使用了Spring 那么怎么集成到JUnit中进行测试呢 首先大家能够想到的肯定是:
 *                  public class TestMain(){
 *                      @Test
 *                      public void test(){
 *                          ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
 *                          TestService service = context.getBean(TestService.class);
 *
 *                          service.test();
 *                      }
 *                  }
 * 直接编写有关测试用例即可 但是这样的话 如果我们有很多个测试用例 那么我们不可能每次测试都去创建ApplicationContext吧 我们可以使用@Before
 * 添加一个测试前动作来提前配置ApplicationContext 但是这样的话 还是不够简便 能不能有更快速高效的方法呢?
 *
 * Spring为我们提供了一个Test模块 它会自动集成JUnit进行测试 我们可以导入一下依赖:
 *                  <dependency>
 *                      <groupId>org.junit.jupiter</groupId>
 *                      <artifactId>junit-jupiter</artifactId>
 *                      <version>5.8.1</version>
 *                      <scope>test</scope>
 *                  </dependency>
 *                  <dependency>
 *                      <groupId>org.springframework</groupId>
 *                      <artifactId>spring-test</artifactId>
 *                      <version>5.3.12</version>
 *                  </dependency>
 * 这里导入的是JUnit5和Spring模块依赖 然后直接在我们的测试类是添加两个注解就可以搞定:
 *                  @ExtendWith(SpringExtension.class)
 *                  @ContextConfiguration(classes = MainConfiguration.class)
 *                  public class MainTest {
 *                      @Resource
 *                      TestService service;
 *
 *                      @Test
 *                      public void test(){
 *                          service.setStudent();
 *                      }
 *                  }
 * @ExtendWith 是由JUnit提供的注解 等同于旧版本的@RunWith注解 然后使用SpringTest模块提供的@ContextConfiguration注解
 * 来表示要加载哪一个配置文件 可以是XML文件也可以是类 我们这里就直接使用类进行加载
 *
 * 配置完成后 我们可以直接使用@Autowired来进行依赖注入 并且直接在测试方法中使用注入的Bean 现在就非常方便了
 */
public class Main {

    public static void main(String[] args) {

    }

}

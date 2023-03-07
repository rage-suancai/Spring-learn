package DatabaseFrameworkIntegration.integrationMybatis;

import DatabaseFrameworkIntegration.integrationMybatis.config.MainConfiguration;
import DatabaseFrameworkIntegration.integrationMybatis.mapper.TestMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 整合Mybatis框架
 * 通过了解数据源 我们已经清楚 Mybatis实际上是在使用自己编写的数据源(数据源实现其实有很多 之后我们再聊其他的)默认使用的是池化数据源 它预先存储了很多的连接对象
 *
 * 那么我们来看一下 如何将Mybatis与Spring更好的结合呢? 比如我们现在希望将SqlSessionFactory交给IoC容器进行管理
 * 而不是我们自己创建工具类来管理(我们之前一直都在使用工具类管理合创建会话)
 *
 *                  <!-- 这两个依赖不用我说了吧 -->
 *                  <dependency>
 *                      <groupId>org.mybatis</groupId>
 *                      <artifactId>mybatis</artifactId>
 *                      <version>3.5.11</version>
 *                  </dependency>
 *                  <dependency>
 *                      <groupId>com.mysql</groupId>
 *                      <artifactId>mysql-connector-j</artifactId>
 *                      <version>8.0.20</version>
 *                  </dependency>
 *                  <!-- Mybatis针对于Spring专门编写的支持框架 -->
 *                  <dependency>
 *                      <groupId>org.mybatis</groupId>
 *                      <artifactId>mybatis-spring</artifactId>
 *                      <version>3.0.1</version>
 *                  </dependency>
 *                  <!-- Spring的JDBC支持框架 -->
 *                  <dependency>
 *                       <groupId>org.springframework</groupId>
 *                       <artifactId>spring-jdbc</artifactId>
 *                       <version>6.0.4</version>
 *                  </dependency>
 *
 * 在mybatis-spring依赖中 为我们提供了SqlSessionTemplate类 它其实就是官方封装的一个工具类 我们可以将其注册为Bean 这样我们随时都可以向IoC容器索要对象
 * 而不用自己去再编写一个工具类了 我们可以直接再配置类中创建 对于这种别人编写的类 如果要注册为Bean 那么只能在配置类中完成:
 *
 *                  @Configuration
 *                  @ComponentScan("DatabaseFrameworkIntegration.IntegrationMybatis.entity")
 *                  public class MainConfiguration {
 *
 *                      @Bean
 *                      public SqlSessionTemplate sqlSessionTemplate() throws IOException {
 *
 *                          SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
 *                          return new SqlSessionTemplate(factory);
 *
 *                      }
 *
 *                  }
 *
 * 这里我们随便编写一个测试的Mapper类:
 *
 *                  @Data
 *                  public class Student {
 *
 *                      private Integer sid;
 *                      private String name;
 *                      private String sex;
 *
 *                  }
 *
 *                  @Mapper
 *                  public interface TestMapper {
 *
 *                      @Select("select * from student where sid = 1")
 *                      Student getStudent();
 *
 *                  }
 *
 * 最后是配置文件:
 *
 *                  <?xml version="1.0" encoding="UTF-8" ?>
 *                  <!DOCTYPE configuration
 *                          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 *                          "http://mybatis.org/dtd/mybatis-3-config.dtd">
 *
 *                  <configuration>
 *
 *                     <environments default="development">
 *                         <environment id="development">
 *                             <transactionManager type="JDBC"/>
 *                             <dataSource type="POOLED">
 *                                 <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
 *                                 <property name="url" value="jdbc:mysql://localhost:3306/study"/>
 *                                 <property name="username" value="root"/>
 *                                 <property name="password" value="123456"/>
 *                             </dataSource>
 *                         </environment>
 *                     </environments>
 *
 *                     <mappers>
 *                       <mapper class="DatabaseFrameworkIntegration.IntegrationMybatis.Mapper.TestMapper"/>
 *                     </mappers>
 *
 *                 </configuration>
 *
 * 我们来测试一下吧:
 *
 *                  public static void main() {
 *
 *                      ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
 *                      TestMapper mapper = context.getBean(TestMapper.class);
 *                      System.out.println(mapper.getStudent());
 *
 *                  }
 *
 *      https://smms.app/image/L83vrESxoXKO7fQ
 *
 * 这样 我们就成功将Mybatis完成了初步整合 直接从容器中就能获取到SqlSessionTemplate 结合自动注入 我们的代码量能够进一步的减少
 *
 * 虽然这样已经很方便了 但是还不够方便 我们依然需要手动去获取Mapper对象 那么能否直接得到对应的Mapper对象呢?
 * 我们希望让Spring直接帮助我们管理所有的Mapper 当需要时 可以直接从容器中获取 我们可以直接在配置类上方添加注解:
 *
 *                  @Configuration
 *                  @ComponentScan("DatabaseFrameworkIntegration.IntegrationMybatis.entity")
 *                  @MapperScan("DatabaseFrameworkIntegration.IntegrationMybatis.mapper")
 *                  public class MainConfiguration {
 *
 * 这样 Spring会自动扫描所有的Mapper 只要是添加了@Mapper注解的类 都可以直接被注册为Bean 那么我们现在就可以直接通过容器获取了:
 *
 *                  public static void main() {
 *
 *                      ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
 *                      TestMapper mapper = context.getBean(TestMapper.class);
 *                      System.out.println(mapper.getStudent());
 *
 *                  }
 *
 * 请一定注意 必须存在SqlSessionTemplate或是SqlSessionFactoryBean的Bean 否则会无法初始化(毕竟要数据库的链接信息)我们接着来看
 * 如果我们希望直接去除Mybatis的配置文件 完全实现全注解配置 那么该怎么去实现呢? 我们可以使用SqlSessionFactoryBean类:
 *
 *                  @Configuration
 *                  @ComponentScan("DatabaseFrameworkIntegration.IntegrationMybatis.entity")
 *                  @MapperScan("DatabaseFrameworkIntegration.IntegrationMybatis.mapper")
 *                  public class MainConfiguration {
 *
 *                      @Bean // 单独创建一个Bean 方便之后更换
 *                      public DataSource dataSource() {
 *
 *                          return new PooledDataSource("com.mysql.cj.jdbc.Driver",
 *                                  "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai",
 *                                  "root", "123456");
 *
 *                      }
 *
 *                      public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
 *
 *                          SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
 *                          bean.setDataSource(dataSource);
 *                          return bean;
 *
 *                      }
 *
 *                  }
 *
 * 首先我们需要创建一个数据源的实现类 因为这是数据库最基本的信息 然后再给到SqlSessionFactoryBean实例 这样
 * 我们相当于直接在一开始通过IoC容器配置了SqlSessionFactory 这里只需要传入一个DataSource的实现即可 我们采用池化数据源:
 *
 * 删除配置文件 重新再来运行 同样可以正常使用Mapper 从这里开始 通过IoC容器 Mybatis已经不再需要使用配置文件了
 * 在我们之后的学习中 基于Spring的开发将不会再出现Mybatis的配置文件
 */
public class Main {

    static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
    static TestMapper mapper = context.getBean(TestMapper.class);

    static void test1() {

        SqlSessionTemplate template = context.getBean(SqlSessionTemplate.class);
        TestMapper mapper = template.getMapper(TestMapper.class);

        System.out.println(mapper.getDept());

    }

    static void test2() {

        System.out.println(mapper.getDept());

    }

    public static void main(String[] args) {

        //test1();
        test2();

    }

}

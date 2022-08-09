package MyBatisThorough.mybatis3;

import MyBatisThorough.mybatis3.config.MainConfiguration;
import MyBatisThorough.mybatis3.mapper.TestMapper;
import MyBatisThorough.mybatis3.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring整合Mybatis框架
 * 通过了解数据源 我们已经清楚 Mybatis实际上是在使用自己编写的数据源(数据源有很多 之后我们再聊其他的) 默认使用的是池化的数据源 它预先存储了很多连接对象
 *
 * 那么我们来看一下 如何将Mybatis于Spring更好的结合呢 比如我们现在希望将SqlSessionFactory交给IoC容器进行管理 而不是我们自己创建工具类来管理(我们之前一直都在使用工具类管理和创建会话)
 *
 * 首先导入依赖:
 *                      <dependency>
 *                          <groupId>org.mybatis</groupId>
 *                          <artifactId>mybatis</artifactId>
 *                          <version>3.5.7</version>
 *                      </dependency>
 *                      <dependency>
 *                          <groupId>org.mybatis</groupId>
 *                          <artifactId>mybatis-spring</artifactId>
 *                          <version>2.0.6</version>
 *                      </dependency>
 *                      <dependency>
 *                          <groupId>mysql</groupId>
 *                          <artifactId>mysql-connector-java</artifactId>
 *                          <version>5.1.43</version>
 *                      </dependency>
 *                      <dependency>
 *                          <groupId>org.springframework</groupId>
 *                          <artifactId>spring-jdbc</artifactId>
 *                          <version>5.3.13</version>
 *                      </dependency>
 * 在mybatis依赖中 为我们提供了SqlSessionTemplate类 它其实就是官方封装的一个工具类 我们可以将其注册为Bean 这样我们随时都可以向IoC容器索要
 * 而不用自己再去编写一个工具类了 我们可以直接在配置类中创建 别忘了mybatis-config.xml数据库配置文件:
 *                      @Configuration
 *                      public class MainConfiguration {
 *                          @Bean
 *                          public SqlSessionTemplate sqlSessionTemplate() throws IOException {
 *                              SqlSessionFactory template = new SqlSessionTemplate(
 *                                  new SqlSessionFactoryBuilder()
 *                                      .build(Resources.getResourceAsReader("mybatis.config.xml")));
 *                              return template;
 *                          }
 *                      }
 *
 *                      public static void main(String[] args) {
 *                           AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
 *                           SqlSessionTemplate template = context.getBean(SqlSessionTemplate.class);
 *
 *                           TestMapper mapper = template.getMapper(TestMapper.class);
 *                           System.out.println(mapper.getStudent());
 *                      }
 *
 *                      public interface TestMapper {
 *                          @Select("select * from student where sid = '1'")
 *                          Student getStudent();
 *                      }
 *
 *                      @Data
 *                      public class Student {
 *                          int sid;
 *                          String name;
 *                          String sex;
 *                      }
 *
 * 最后成功得到Student实体类 证明SqlSessionTemplate成功注册为Bean可以使用了 虽然这样已经很方便了 但是还不够方便 我们依然需要手动去获取Mapper对象
 * 那么能否直接得到对应的Mapper对象呢 我们希望让Spring直接帮助我们管理所有的Mapper 当需要时 可以直接从容器中获取 我们可以直接在配置类上添加注解:
 *                      @MapperScan("MyBatisThorough.mybatis3.mapper")
 * 这样 Spring会自动扫描所有的Mapper 并将其实现注册为Bean 那么我们现在就可以直接通过容器获取了:
 *                      TestService service = context.getBean(TestService.class);
 *                      System.out.println(service.getStudent());
 * 请一定注意: 必须存在SqlSessionTemplate或是SqlSessionFactoryBean的Bean 否则会无法初始化(毕竟要数据库的链接信息)
 *
 * 我们接着来看 如果我们希望直接去除Mybatis的配置文件 那么该怎么去实现呢 我们可以使用SqlSessionFactoryBean类:
 *                      @Configuration
 *                      @MapperScan("MyBatisThorough.mybatis3.mapper")
 *                      @ComponentScan("MyBatisThorough.mybatis3.service")
 *                      public class MainConfiguration {
 *
 *                          @Bean
 *                          public DataSource dataSource(){
 *                              PooledDataSource dataSource = new PooledDataSource();
 *                              dataSource.setDriver("com.mysql.jdbc.Driver");
 *                              dataSource.setUrl("jdbc:mysql//localhost:3306/study");
 *                              dataSource.setUsername("root");
 *                              dataSource.setPassword("123456");
 *                              return dataSource;
 *                          }
 *                          @Bean
 *                          public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource source){
 *                              SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
 *                              bean.setDataSource(source);
 *                              return bean;
 *                          }
 *
 *                      }
 * 首先我们需要创建一个数据源的实现类 因为只是数据库最基本的信息 然后再给到SqlSessionFactoryBean实例
 * 这样 相当于直接在一开始通过IoC容器配置了SqlSessionFactory 只需要传入一个DataSource的实现即可
 *
 * 删除配置文件 重新在来运行 同样可以正常使用Mapper 从这里开始 通过IoC容器 Mybatis已经不再需要使用配置文件了 之后 基于Spring的开发将不会再出现Mybatis的配置文件
 *
 * 使用HikariCP连接池
 * 前面我们提到了数据源还有其他实现 比如C3P0 Druid等 它们都是非常优秀的数据源实现(可以自行了解) 不过我们这里要介绍的 是之后在SpringBoot中还会遇到的HikariCP连接池
 *      HikariCP是由日本程序员开源的一个数据库连接池组件 代码非常轻量 并且速度非常的快 根据官方提供的数据 在酷睿i7开启32个线程32个连接的情况下
 *      进行随机数据库读写操作 HikariCP的速度是现在常用的C3P0数据库连接池的数百倍 在SpringBoot2.0中 官方也是推荐使用HikariCP
 *
 * 首先 我们需要导入依赖:
 *                  <dependency>
 *                      <groupId>com.zaxxer</groupId>
 *                      <artifactId>HikariCP</artifactId>
 *                      <version>3.4.5</version>
 *                  </dependency>
 * 接着修改一下Bean的定义:
 *                  @Bean
 *                  public DataSource dataSource(){
 *                      HikariDataSource dataSource = new HikariDataSource();
 *                      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
 *                      dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/study");
 *                      dataSource.setUsername("root");
 *                      dataSource.setPassword("123456");
 *                      return dataSource;
 *                  }
 * 最后我们发现 同样可以得到输出结果 但是出现了一个报错:
 *                  SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
 *                  SLF4J: Defaulting to no-operation (NOP) logger implementation
 *                  SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
 * 此数据源实际上是采用了SLF4J日志框架打印日志信息 但是现在没有任何的日志实现(slf4j只是一个API标准 它规范了多种日志框架的操作 统一使用SLF4J定义的方法来操作不同的日志框架)
 * 我们这里就使用JUL作为日志实现 我们需要导入另一个依赖:
 *                  <dependency>
 *                      <groupId>org.slf4j</groupId>
 *                      <artifactId>slf4j-jdk14</artifactId>
 *                      <version>1.7.25</version>
 *                  </dependency>
 * 注意: 版本一定要和slf4j-api保持一致
 *
 * 这样就能得到我们的日志信息了:
 *                  7月 19, 2022 2:40:55 上午 com.zaxxer.hikari.HikariDataSource getConnection
 *                  信息: HikariPool-1 - Starting...
 *                  7月 19, 2022 2:40:55 上午 com.zaxxer.hikari.HikariDataSource getConnection
 *                  信息: HikariPool-1 - Start completed.
 *
 * 在SpringBoot阶段 我们还会遇到HikariPool-1-Starting 和HikariPool-1-Start completed 同款日志信息
 *
 * 当然Lombok肯定也是支持这个日志框架快速注解的:
 *                  @Slf4j
 */
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        //SqlSessionTemplate template = context.getBean(SqlSessionTemplate.class);

        /*TestMapper mapper = template.getMapper(TestMapper.class);
        System.out.println(mapper.getStudent());*/

        TestService service = context.getBean(TestService.class);
        System.out.println(service.getStudent());

    }

}

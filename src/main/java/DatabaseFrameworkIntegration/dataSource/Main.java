package DatabaseFrameworkIntegration.dataSource;

/**
 * 了解数据源
 * 在之前 我们如果需要创建一个JDBC的连接 那么必须使用DriverManager.getConnection()来创建连接 连接建立后 我们才可以进行数据库操作 而学习了Mybatis之后
 * 我们就不用再去使用DriverManager为我们提供连接对象 而是直接使用Mybatis为我们提供的SqlSessionFactory工具类来获取对应的 SqlSession通过会话对象去操作数据库
 *
 * 那么 它到底是如何封装JDBC的呢? 我们可以试着来猜想一下 会不会是Mybatis每次都是帮助我们调用DriverManager来实现的数据库连接创建? 我们可以看看Mybatis的源码:
 *
 *                  public SqlSession openSession(boolean autoCommit) {
 *                      return this.openSessionFromDataSource(this.configuration.getDefaultExecutorType(), (TransactionIsolationLevel)null, autoCommit);
 *                  }
 *
 * 在通过SqlSessionFactory调用openSession方法之后 它调用了内部的一个私有的方法openSSessionFromDataSource 我们接着来看 这个方法里面定义了什么内容:
 *
 *                  private SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
 *                      Transaction tx = null;
 *
 *                      DefaultSqlSession var8;
 *                      try {
 *                          // 获取当前环境(由配置文件映射的对象实体)
 *                          Environment environment = this.configuration.getEnvironment();
 *                          // 事务工厂(暂时不提 下一板块讲解)
 *                          TransactionFactory transactionFactory = this.getTransactionFactoryFromEnvironment(environment);
 *                          // 配置文件中: <transactionManager type="JDBC"/>
 *                          // 生成事务 (根据我们的配置 会默认生成JdbcTransaction)这里是关键 我们看到这里用到了environment.getDataSource()方法
 *                          tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);
 *                          // 执行器 包括全部的数据库操作方法定义 本质上是在使用执行器操作数据库 需要传入事务对象
 *                          Executor executor = this.configuration.newExecutor(tx, execType);
 *                          // 封装为SqlSession对象
 *                          var8 = new DefaultSqlSession(this.configuration, executor, autoCommit);
 *                      } catch (Exception var12) {
 *                          this.closeTransaction(tx);
 *                          throw ExceptionFactory.wrapException("Error opening session.  Cause: " + var12, var12);
 *                      } finally {
 *                          ErrorContext.instance().reset();
 *                      }
 *
 *                      return var8;
 *                  }
 *
 * 也就是说 我们的数据源配置信息 存放在了Transaction对象中 那么现在我们只需要知道执行器到底是如何执行SQL语句的
 * 我们就知道到底如何创建Connection对象了 这时就需要获取数据库的链接信息了 那么我们来看看 这个DataSource到底是个什么:
 *
 *                  public interface DataSource  extends CommonDataSource, Wrapper {
 *
 *                    Connection getConnection() throws SQLException;
 *
 *                    Connection getConnection(String username, String password)
 *                      throws SQLException;
 *                  }
 *
 * 我们发现 它是在javax.sql定义的一个接口 它包括了两个方法 都是用于获取连接的 因此 现在我们可以断定
 * 并不是通过之前DriverManager的方法去获取连接了 而是使用DataSource的实现类来获取的 因此 也就正式引入到我们这一章节的话题了:
 *
 *      数据库连接的建立和关闭是极其耗费系统资源的操作 通过DriverManager获取的数据库连接 一个数据库连接对象均对应一个物理数据库连接
 *      每次操作都打开一个物理连接 使用完后立即关闭连接 频繁的打开 关闭连接会持续消耗网络资源 造成整个系统性能的低下
 *
 * 因此 JDBC为我们定义了一个数据源的标准 也就是DataSource接口 告诉数据源数据库是的连接信息 并将所有的连接全部交给数据源进行集中管理
 * 当需要一个Connection对象时 可以向数据源申请 数据源会根据内部机制 合理地分配连接对象给我们
 *
 * 一般比较常用的DataSource实现 都是采用池化技术 就是在一开始就创建好N个连接 这样之后使用就无需再次进行连接 而是直接使用现成的Connection对象进行数据库操作:
 *
 *
 *      https://smms.app/image/rk4mcdvYn6osOLW
 *
 * 当然 也可以使用传统的即用即连的方式获取Connection对象 Mybatis为我们提供了几个默认的数据源实现 我们之前一直在使用的是官方的默认配置 也就是池化数据源:
 *
 *                  <?xml version="1.0" encoding="UTF-8" ?>
 *                  <!DOCTYPE configuration
 *                          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
 *                          "http://mybatis.org/dtd/mybatis-3-config.dtd">
 *                  <configuration>
 *                      <environments default="development">
 *                          <environment id="development">
 *                              <transactionManager type="JDBC"/>
 *                              <dataSource type="POOLED">
 *                                  <property name="driver" value="${驱动类(含包名)}"/>
 *                                  <property name="url" value="${数据库连接URL}"/>
 *                                  <property name="username" value="${用户名}"/>
 *                                  <property name="password" value="${密码}"/>
 *                              </dataSource>
 *                          </environment>
 *                      </environments>
 *                  </configuration>
 *
 * 这里的type属性一共三个选项:
 *
 *      > UNPOOLED 不使用连接池的数据源
 *      > POOLED 使用连接池的数据源
 *      > JNDI 使用JNDI实现的数据源
 */
public class Main {

    public static void main(String[] args) {

        // SqlSessionFactory
        System.out.println("Hello DataSource🍔");

    }

}

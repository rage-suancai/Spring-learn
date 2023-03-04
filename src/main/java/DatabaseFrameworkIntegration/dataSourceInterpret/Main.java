package DatabaseFrameworkIntegration.dataSourceInterpret;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * 解读Mybatis数据源实现(选学)
 * 前面我们介绍了DataSource数据源 那么我们就来看看 Mybatis到底是怎么实现的
 * 我们先来看看 不使用池化的数据源实现 它叫做UnpooledDataSource 我们来看看源码:
 *
 *                  public class UnpooledDataSource implements DataSource {
 *                      private ClassLoader driverClassLoader;
 *                      private Properties driverProperties;
 *                      private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap();
 *                      private String driver;
 *                      private String url;
 *                      private String username;
 *                      private String password;
 *                      private Boolean autoCommit;
 *                      private Integer defaultTransactionIsolationLevel;
 *                      private Integer defaultNetworkTimeout;
 *                        ...
 *
 * 首先这个类中定义了很多的成员 包括数据库的连接信息 数据库驱动信息 事务相关信息等 我们接着来看 它是如何实现DataSource中提供的接口方法的:
 *
 *                  public Connection getConnection() throws SQLException {
 *                      return this.doGetConnection(this.username, this.password);
 *                  }
 *
 *                  public Connection getConnection(String username, String password) throws SQLException {
 *                      return this.doGetConnection(username, password);
 *                  }
 *
 * 实际上 这两个方法都指向了内部的一个doGetConnection方法 那么我们接着来看:
 *
 *                  private Connection doGetConnection(String username, String password) throws SQLException {
 *                      Properties props = new Properties();
 *                      if (this.driverProperties != null) {
 *                          props.putAll(this.driverProperties);
 *                      }
 *
 *                      if (username != null) {
 *                          props.setProperty("user", username);
 *                      }
 *
 *                      if (password != null) {
 *                          props.setProperty("password", password);
 *                      }
 *
 *                      return this.doGetConnection(props);
 *                  }
 *
 * 这里将用户名和密码配置封装为一个Properties对象 然后执行另一个重载同名的方法:
 *
 *                  private Connection doGetConnection(Properties properties) throws SQLException {
 *                        // 若未初始化驱动 需要先初始化 内部维护了一个Map来记录初始化信息 这里不多介绍了
 *                      this.initializeDriver();
 *                        // 传统的获取连接的方式 是不是终于找到熟悉的味道了
 *                      Connection connection = DriverManager.getConnection(this.url, properties);
 *                        // 对连接进行额外的一些配置
 *                      this.configureConnection(connection);
 *                      return connection;   // 返回得到的Connection对象
 *                  }
 *
 * 到这里 就返回Connection对象了 而此对象正是通过DriverManager来创建的 因此 非池化的数据源实现依然使用的是传统的连接创建方式
 * 那我们接着来看池化的数据源实现 它是PooledDataSource类:
 *
 *                  public class PooledDataSource implements DataSource {
 *                  private static final Log log = LogFactory.getLog(PooledDataSource.class);
 *                  private final PoolState state = new PoolState(this);
 *                    // 内部维护了一个非池化的数据源 是要干嘛?
 *                  private final UnpooledDataSource dataSource;
 *                  protected int poolMaximumActiveConnections = 10;
 *                  protected int poolMaximumIdleConnections = 5;
 *                  protected int poolMaximumCheckoutTime = 20000;
 *                  protected int poolTimeToWait = 20000;
 *                  protected int poolMaximumLocalBadConnectionTolerance = 3;
 *                  protected String poolPingQuery = "NO PING QUERY SET";
 *                  protected boolean poolPingEnabled;
 *                  protected int poolPingConnectionsNotUsedFor;
 *                  private int expectedConnectionTypeCode;
 *                    // 并发相关类 我们在JUC篇视频教程中介绍过 感兴趣可以前往观看
 *                  private final Lock lock = new ReentrantLock();
 *                  private final Condition condition;
 *
 * 我们发现 在这里的定义就比非池化的实现复杂得多了 因为它还要考虑并发的问题 并且还要考虑如何合理地存放大量的链接对象 该如何进行合理分配
 * 因此它的玩法非常之高级 再高级的玩法 我们都要拿下
 *
 * 首先注意 它存放了一个UnpooledDataSource 此对象是在构造时就被创建 其实创建Connection还是依靠数据库驱动创建 我们后面慢慢解析 首先我们来看看它是如何实现接口方法的:
 *
 *                  public Connection getConnection() throws SQLException {
 *                      return this.popConnection(this.dataSource.getUsername(), this.dataSource.getPassword()).getProxyConnection();
 *                  }
 *
 *                  public Connection getConnection(String username, String password) throws SQLException {
 *                      return this.popConnection(username, password).getProxyConnection();
 *                  }
 *
 * 可以看到 它调用了popConnection()方法来获取连接对象 然后进行了一个代理 通过正这方法名字我们可以猜测
 * 有可能整个连接池就是一个类似于栈的集合类型结构实现的 那么我们接着来看看popConnection方法:
 *
 *                  private PooledConnection popConnection(String username, String password) throws SQLException {
 *                          boolean countedWait = false;
 *                          // 返回的是PooledConnection对象
 *                          PooledConnection conn = null;
 *                          long t = System.currentTimeMillis();
 *                          int localBadConnectionCount = 0;
 *
 *                          while(conn == null) {
 *                              synchronized(this.state) { // 加锁 因为有可能很多个线程都需要获取连接对象
 *                                  PoolState var10000;
 *                                  // PoolState存了两个List 一个是空闲列表 一个是活跃列表
 *                                  if (!this.state.idleConnections.isEmpty()) { // 有空闲的连接时 可以直接分配Connection
 *                                      conn = (PooledConnection)this.state.idleConnections.remove(0); // ArrayList中取第一个元素
 *                                      if (log.isDebugEnabled()) {
 *                                          log.debug("Checked out connection " + conn.getRealHashCode() + " from pool.");
 *                                      }
 *                                  // 如果已经没有多余的连接可以分配 那么就检查一下活跃连接数是否达到最大的分配上限 如果没有 就new一个新的
 *                                  } else if (this.state.activeConnections.size() < this.poolMaximumActiveConnections) {
 *                                      // 注意new了之后并没有立即往List里面塞 只是存了一些基本信息
 *                                      // 我们发现 这里依靠UnpooledDataSource创建了一个Connection对象 并将其封装到PooledConnection中
 *                                      // 所以说内部维护的UnpooledDataSource对象其实是为了节省代码 因为创建数据库连接其实都是一样的方式
 *                                      conn = new PooledConnection(this.dataSource.getConnection(), this);
 *                                      if (log.isDebugEnabled()) {
 *                                          log.debug("Created connection " + conn.getRealHashCode() + ".");
 *                                      }
 *                                  // 以上条件都不满足 那么只能从之前的连接中寻找了 看看有没有那种卡住的链接(比如 由于网络问题有可能之前的连接一直被卡住 然而正常情况下早就结束并且可以使用了 所以这里相当于是优化也算是一种捡漏的方式)
 *                                  } else {
 *                                      // 获取最早创建的连接
 *                                      PooledConnection oldestActiveConnection = (PooledConnection)this.state.activeConnections.get(0);
 *                                      long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
 *                                      // 判断是否超过最大的使用时间
 *                                      if (longestCheckoutTime > (long)this.poolMaximumCheckoutTime) {
 *                                          // 超时统计信息(不重要)
 *                                          ++this.state.claimedOverdueConnectionCount;
 *                                          var10000 = this.state;
 *                                          var10000.accumulatedCheckoutTimeOfOverdueConnections += longestCheckoutTime;
 *                                          var10000 = this.state;
 *                                          var10000.accumulatedCheckoutTime += longestCheckoutTime;
 *                                          // 从活跃列表中移除此链接信息
 *                                          this.state.activeConnections.remove(oldestActiveConnection);
 *                                          // 如果开启事务 还需要回滚一下
 *                                          if (!oldestActiveConnection.getRealConnection().getAutoCommit()) {
 *                                              try {
 *                                                  oldestActiveConnection.getRealConnection().rollback();
 *                                              } catch (SQLException var15) {
 *                                                  log.debug("Bad connection. Could not roll back");
 *                                              }
 *                                          }
 *
 *                                          // 这里就根据之前的连接对象直接new一个新的连接(注意使用的还是之前的Connection对象 并没有创建新的对象 只是被重新封装了)
 *                                          conn = new PooledConnection(oldestActiveConnection.getRealConnection(), this);
 *                                          conn.setCreatedTimestamp(oldestActiveConnection.getCreatedTimestamp());
 *                                          conn.setLastUsedTimestamp(oldestActiveConnection.getLastUsedTimestamp());
 *                                          // 过期
 *                                          oldestActiveConnection.invalidate();
 *                                          if (log.isDebugEnabled()) {
 *                                              log.debug("Claimed overdue connection " + conn.getRealHashCode() + ".");
 *                                          }
 *                                      } else {
 *                                          // 没有超时 那就确实是没连接可以用了 只能卡住了(阻塞)
 *                                          // 然后顺手记录一下目前有几个线程在等待其他的任务搞完
 *                                          try {
 *                                              if (!countedWait) {
 *                                                  ++this.state.hadToWaitCount;
 *                                                  countedWait = true;
 *                                              }
 *
 *                                              if (log.isDebugEnabled()) {
 *                                                  log.debug("Waiting as long as " + this.poolTimeToWait + " milliseconds for connection.");
 *                                              }
 *                                              // 最后再等等
 *                                              long wt = System.currentTimeMillis();
 *                                              this.state.wait((long)this.poolTimeToWait);
 *                                              // 要是超过等待时间还是没等到 只能放弃了
 *                                              // 注意这样的话con就为null了
 *                                              var10000 = this.state;
 *                                              var10000.accumulatedWaitTime += System.currentTimeMillis() - wt;
 *                                          } catch (InterruptedException var16) {
 *                                              break;
 *                                          }
 *                                      }
 *                                  }
 *
 *                                  // 经过之前的操作 并且已经成功分配到连接对象的情况下
 *                                  if (conn != null) {
 *                                      if (conn.isValid()) { // 首先验证是否有效
 *                                          if (!conn.getRealConnection().getAutoCommit()) { // 清理之前可能存在的遗留事务操作
 *                                              conn.getRealConnection().rollback();
 *                                          }
 *
 *                                          conn.setConnectionTypeCode(this.assembleConnectionTypeCode(this.dataSource.getUrl(), username, password));
 *                                          conn.setCheckoutTimestamp(System.currentTimeMillis());
 *                                          conn.setLastUsedTimestamp(System.currentTimeMillis());
 *                                          // 添加到活跃表中
 *                                          this.state.activeConnections.add(conn);
 *                                          // 统计信息(不重要)
 *                                          ++this.state.requestCount;
 *                                          var10000 = this.state;
 *                                          var10000.accumulatedRequestTime += System.currentTimeMillis() - t;
 *                                      } else {
 *                                          // 无效的连接 直接抛异常
 *                                          if (log.isDebugEnabled()) {
 *                                              log.debug("A bad connection (" + conn.getRealHashCode() + ") was returned from the pool, getting another connection.");
 *                                          }
 *
 *                                          ++this.state.badConnectionCount;
 *                                          ++localBadConnectionCount;
 *                                          conn = null;
 *                                          if (localBadConnectionCount > this.poolMaximumIdleConnections + this.poolMaximumLocalBadConnectionTolerance) {
 *                                              if (log.isDebugEnabled()) {
 *                                                  log.debug("PooledDataSource: Could not get a good connection to the database.");
 *                                              }
 *
 *                                              throw new SQLException("PooledDataSource: Could not get a good connection to the database.");
 *                                          }
 *                                      }
 *                                  }
 *                              }
 *                          }
 *
 *                          // 最后该干嘛干嘛 要是之前拿到的con是null的话 直接抛异常
 *                          if (conn == null) {
 *                              if (log.isDebugEnabled()) {
 *                                  log.debug("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
 *                              }
 *
 *                              throw new SQLException("PooledDataSource: Unknown severe error condition.  The connection pool returned a null connection.");
 *                          } else {
 *                              return conn; // 否则正常返回
 *                          }
 *                      }
 *
 * 经过上面一顿猛如虎的操作之后 我们可以得到以下信息:
 *
 *      如果最后得到了连接对象(有可能是从空闲列表中得到 有可能是直接创建的新的 还有可能是经过回收策略回收得到的)
 *      那么连接(Connection)对象一定会被放在活跃列表中(state.activeConnections)
 *
 * 那么肯定有一个疑问 现在我们已经知道获取一个链接会直接进入到活跃列表中 那么如果一个连接被关闭 又会发生什么事情呢
 * 我们来看看此方法返回之后 会调用getProxyConnection来获取一个代理对象 实际上就是PooledConnection类:
 *
 *                  class PooledConnection implements InvocationHandler {
 *                    private static final String CLOSE = "close";
 *                    private static final Class<?>[] IFACES = new Class[]{Connection.class};
 *                    private final int hashCode;
 *                    // 会记录是来自哪一个数据源创建的的
 *                    private final PooledDataSource dataSource;
 *                    // 连接对象本体
 *                    private final Connection realConnection;
 *                    // 代理的链接对象
 *                    private final Connection proxyConnection;
 *                      ...
 *
 * 它直接代理了构造方法中传入的Connection对象 也是使用JDK的动态代理实现的 那么我们来看一下 它是如何进行代理的:
 *
 *                  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 *                      String methodName = method.getName();
 *                      // 如果调用的是Connection对象的close方法
 *                      if ("close".equals(methodName)) {
 *                          // 这里并不会真的关闭连接(这也是为什么用代理) 而是调用之前数据源的pushConnection方法 将此连接改为为空闲状态
 *                          this.dataSource.pushConnection(this);
 *                          return null;
 *                      } else {
 *                          try {
 *                              if (!Object.class.equals(method.getDeclaringClass())) {
 *                                  this.checkConnection();
 *                                  // 任何操作执行之前都会检查连接是否可用
 *                              }
 *
 *                              // 原方法该干嘛干嘛
 *                              return method.invoke(this.realConnection, args);
 *                          } catch (Throwable var6) {
 *                              throw ExceptionUtil.unwrapThrowable(var6);
 *                          }
 *                      }
 *                  }
 *
 * 这下 池化数据源的大致流程其实就已经很清晰了 那么我们最后在来看看 pushConnection方法:
 *
 *                  protected void pushConnection(PooledConnection conn) throws SQLException {
 *                      synchronized(this.state) { // 老规矩 先来把锁
 *                          // 先从活跃列表移除此连接
 *                          this.state.activeConnections.remove(conn);
 *                          // 判断此链接是否可用
 *                          if (conn.isValid()) {
 *                              PoolState var10000;
 *                              // 看看闲置列表容量是否已满(容量满了就回不去了)
 *                              if (this.state.idleConnections.size() < this.poolMaximumIdleConnections && conn.getConnectionTypeCode() == this.expectedConnectionTypeCode) {
 *                                  var10000 = this.state;
 *                                  var10000.accumulatedCheckoutTime += conn.getCheckoutTime();
 *                                  if (!conn.getRealConnection().getAutoCommit()) {
 *                                      conn.getRealConnection().rollback();
 *                                  }
 *
 *                                  // 把唯一有用的Connection对象拿出来 然后重新创建一个PooledConnection包装
 *                                  PooledConnection newConn = new PooledConnection(conn.getRealConnection(), this);
 *                                  // 放入闲置列表 成功回收
 *                                  this.state.idleConnections.add(newConn);
 *                                  newConn.setCreatedTimestamp(conn.getCreatedTimestamp());
 *                                  newConn.setLastUsedTimestamp(conn.getLastUsedTimestamp());
 *                                  conn.invalidate();
 *                                  if (log.isDebugEnabled()) {
 *                                      log.debug("Returned connection " + newConn.getRealHashCode() + " to pool.");
 *                                  }
 *
 *                                  this.state.notifyAll();
 *                              } else {
 *                                  var10000 = this.state;
 *                                  var10000.accumulatedCheckoutTime += conn.getCheckoutTime();
 *                                  if (!conn.getRealConnection().getAutoCommit()) {
 *                                      conn.getRealConnection().rollback();
 *                                  }
 *
 *                                  conn.getRealConnection().close();
 *                                  if (log.isDebugEnabled()) {
 *                                      log.debug("Closed connection " + conn.getRealHashCode() + ".");
 *                                  }
 *
 *                                  conn.invalidate();
 *                              }
 *                          } else {
 *                              if (log.isDebugEnabled()) {
 *                                  log.debug("A bad connection (" + conn.getRealHashCode() + ") attempted to return to the pool, discarding connection.");
 *                              }
 *
 *                              ++this.state.badConnectionCount;
 *                          }
 *
 *                      }
 *                  }
 *
 * 这样 我们就已经完全了解了Mybatis的池化数据源的执行流程了 只不过 无论Connection管理方式如何变换 无论数据源再高级
 * 我们要知道 它都最终都会使用DriverManager来创建连接对象 而最终使用的也是DriverManager提供的Connection对象
 */
public class Main {

    public static void main(String[] args) {

        // SqlSessionFactory

    }

}

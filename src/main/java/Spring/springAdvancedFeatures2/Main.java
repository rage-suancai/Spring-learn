package Spring.springAdvancedFeatures2;

import Spring.springAdvancedFeatures2.config.MainConfiguration;
import Spring.springAdvancedFeatures2.entity.Student2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 任务调度
 * 为了执行某些任务 我们可能需要一些非常规的操作 比如我们希望使用多线程来处理我们的结果或是执行一些定时任务 到达指定时间再去执行
 * 这时我们首先想到的就是创建一个新的线程来处理 或是使用TimerTask来完成定时任务 但是我们有了Spring框架之后 就不用这样了
 * 因为Spring框架为我们提供了更加便捷的方式进行任务调度
 *
 *                  @EnableAsync
 *                  @Configuration @ComponentScan("Spring.springAdvancedFeatures2.entity")
 *                  public class mainConfiguration {
 *                  }
 *
 * 接着我们只需要在异步执行的方法上 添加@Async注解即可将此方法标记为异步 当此方法被调用时会异步执行 也就是新开一个线程执行 而不是在当前线程执行 我们来测试一下:
 *
 *                  @Component
 *                  public class Student2 {
 *
 *                      public void syncTest() {
 *
 *                          System.out.println(Thread.currentThread().getName() + "我是同步执行的方法 开始...");
 *                          try {
 *                              Thread.sleep(2000);
 *                          } catch (InterruptedException e) {
 *                              throw new RuntimeException(e);
 *                          }
 *                          System.out.println("我是同步执行的方法 结束");
 *
 *                      }
 *
 *                      @Async
 *                      public void asyncTest() {
 *
 *                          System.out.println(Thread.currentThread().getName() + "我是异步执行的方法 开始...");
 *                          try {
 *                              Thread.sleep(2000);
 *                          } catch (InterruptedException e) {
 *                              throw new RuntimeException(e);
 *                          }
 *                          System.out.println("我是异步执行的方法 结束...");
 *
 *                      }
 *
 *                  }
 *
 * 现在我们在主方法中分别调用一下试试看:
 *
 *                  public static void main() {
 *                      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
 *                      Student7 student7 = context.getBean(Student7.class);
 *                      student7.asyncTest(); // 异步执行
 *                      student7.syncTest(); // 同步执行
 *                  }
 *
 *      https://img-blog.csdnimg.cn/img_convert/cc206a7190773661025bc7d0651538fa.png
 *
 * 很明显 异步执行的任务并不是在当前线程启动的 而是在其他线程启动的 所以说并不会在当前线程阻塞 可以看到马上就开始执行下一行代码 调用同步执行的任务了
 *
 * 因此 当我们要将Bean的某个方法设计为异步执行时 就可以直接添加这个注解 但是需要注意
 * 添加此注解要求方法的返回值只能是void或是Future才可以 (Future类型我们在JUC篇文章中有详细介绍)
 *
 * 还有 在使用时 可能还会出现这样的信息:
 *
 *      https://img-blog.csdnimg.cn/img_convert/92696d1e066da35fe031e0f443d51ab2.png
 *
 * 虽然出现了这样的信息 但是我们的程序依然可以正常运行 这里因为Spring默认会从容器中选择一个Executor类型(这同样是在JUC篇视频教程中介绍的类型)的实例
 * 并使用它来创建线程执行任务 这是Spring推荐的方式 当然 如果没有找到 那么会使用自带的SimpleAsyncTaskExecutor处理异步方法调用
 *
 * 肯定会有小伙伴疑惑 什么情况?! 这个方法很明显我们并没有去编写异步执行的逻辑 那么为什么会异步执行呢? 这里很明显是同步调用的方法啊
 * 的确 如果这个Bean这是一个简简单单的Student类型的对象 确实做不到 但是它真的只是一个简简单单的Student类型对象吗?
 *
 *                  Student2 student2 = context.getBean(Student2.class);
 *                  System.out.println(student2.getClass); // 这里我们通过getClass来获取一下类型 你会发现惊喜
 *
 * 我们来看看结果:
 *
 *                  class Spring.springAdvancedFeatures2.entity.Student2$$SpringCGLIB$$0
 *
 * ???这是什么东西? 这实际上是Spring帮助我们动态成的一个代理类 我们原来的类代码已经被修改了 当然 这只是冰山一角
 * 更多的内容 我们还会再AOP面向切面部分中继续为大家进行介绍 能做到这样的操作 这其实都是AOP的功劳
 *
 * 看完了异步任务 我们接着来看定时任务 定时任务其实就是指定再哪个时候再去执行 再JavaSE阶段我们使用过TimerTask来执行定时任务
 * Spring中的定时任务是全局性质的 当我们的Spring程序启动后 那么定时任务也就跟着启动了 我们可以在配置类是添加@EnableScheduling注解:
 *
 *                  @EnableScheduling
 *                  @Configuration @ComponentScan("Spring.springAdvancedFeatures2.entity")
 *                  public class MainConfiguration {
 *                  }
 *
 * 接着我们可以直接在配置类里面编写定时任务 把我们要做的任务写成方法 并添加@Scheduled注解:
 *
 *                  @Scheduled(fixedRate = 2000) // 单位依然是毫秒 这里是每两秒钟打印一次
 *                  public void task() {
 *                      System.out.println("我是定时任务! " + new Date());
 *                  }
 *
 *      https://img-blog.csdnimg.cn/img_convert/bf3e1e38a6f202291579b02f41b42bda.png
 *
 * 我们注意到@Scheduled中有很多参数 我们需要指定 cron, fixedDelay(String), or fixedRete(String)的其中一个 否则无法创建定时任务 他们的区别如下:
 *
 *      > fixedDelay: 在上一次定时任务执行完之后 间隔多久继续执行
 *      > fixedRate: 无论上一次定时任务有没有执行完成 两次任务之间的时间间隔
 *      > cron: 如果嫌上面两个不够灵活 你还可以使用cron表达式来指定任务计划
 *
 * 这里简单讲解一下cron表达式: https://blog.csdn.net/sunnyzyq/article/details/98597252
 */
public class Main {

    static void test1() throws InterruptedException {

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        /*Student2 student2 = context.getBean(Student2.class);
        System.out.println("任务开始");
        student2.syncTest();
        System.out.println("任务结束");*/

        /*Student2 student2 = context.getBean(Student2.class);
        System.out.println("任务开始");
        student2.asyncTest();
        System.out.println("任务结束");*/

        /*Student2 student2 = context.getBean(Student2.class);
        System.out.println(student2.getClass());*/

        Student2 student2 = context.getBean(Student2.class);
        student2.task();

    }

    public static void main(String[] args) throws InterruptedException {

        test1();

    }

}

package Spring.springAdvancedFeatures3;

import Spring.springAdvancedFeatures3.config.MainConfiguration;
import Spring.springAdvancedFeatures3.entity.TaskComponent;
import Spring.springAdvancedFeatures3.entity.TestListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 监听器
 * 监听器对我们来说也是一个比较陌生的概念 那么何谓监听呢?
 *
 * 监听实际上就是等待某个事件的触发 当事件触发时 对应事件的监听器就会被通知 如果你学习过JavaSwing篇文章
 * 应该会深有体会 监听器可是很关键的 只不过在Spring中用的不是很频繁罢了 但是这里还是要简单介绍一下:
 *
 *                  @Component
 *                  public class TestLister implements ApplicationLister<ContextRefreshedEvent> {
 *
 *                      public void onApplicationEvent(ContextRefreshedEvent event) {
 *
 *                          System.out.println(event.getApplicationContext()); // 可以直接通过事件获取到事件相关的东西
 *
 *                      }
 *
 *                  }
 *
 * 要编写监听器 我们只需要让Bean继承ApplicationListener就可以了 并且将类型指定为对应的Event事件 这样
 * 当发生某个事件时就会通知我们 比如ContextRefreshedEvent 这个事件会在Spring容器初始化完成会触发一次:
 *
 *                  org.springframework.context.annotation.AnnotationConfigApplicationContext@7591083d, started on Mon Feb 27 14:19:44 HKT 2023
 *
 * 是不是感觉挺智能的? Spring内部有各种各样的事件 当然我们也可以自己编写事件 然后在某个时刻发布这个事件到所有的监听器:
 *
 *                  public class TestEvent extends ApplicationEvent { // 自定义事件需要继承ApplicationEvent
 *
 *                      public TestEvent(Object source) {
 *                          super(source);
 *                      }
 *
 *                  }
 *
 *                  @Component
 *                  public class TestListener implements ApplicationListener<TestEvent> {
 *                      @Override
 *                      public void onApplicationEvent() {
 *                          System.out.println("发生了一次自定义事件 成功监听到");
 *                      }
 *                  }
 *
 * 比如现在我们希望在定时任务中每秒钟发生一次这个事件:
 *
 *                  @Component
 *                  public class TaskComponent implements ApplicationEventPublisherAware {
 *                      // 要发布事件 需要拿到ApplicationEvenPublisher 这里我们通过Aware在初始化时候拿到
 *                      // 实际上我们的ApplicationContext就是ApplicationEventPublisher的实现类 这里拿到的就是我们创建的ApplicationContext对象
 *                      ApplicationEventPublisher publisher;
 *
 *                      @Scheduled(fixedRate = 1000) // 一秒一次
 *                      public void task() {
 *                          // 直接通过ApplicationEventPublisher的publishEvent方法发布事件
 *                          // 这样 所有这个事件的监听器 都会监听到这个事件发生了
 *                          publisher.publishEvent(new TestEvent(this));
 *
 *                      }
 *
 *                      @Override
 *                      public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
 *
 *                          this.publisher = publisher;
 *
 *                      }
 *
 *                  }
 *
 * 此时 发布事件旁边出现了图标 说明就可以了:
 *
 *      https://img-blog.csdnimg.cn/img_convert/d8dc31d3058d28a238f2c6d7b369c785.png
 *
 * 我们可以点击这个图标快速跳转到哪里监听这个事件 IDEA这些细节做的还是挺好的 我们来看看运行结果吧:
 *
 *      https://img-blog.csdnimg.cn/img_convert/697805a901fe1d91d417798f8ca4165c.png
 *
 * 是不是感觉好像也没那么难 这套机制其实还挺简单的 这样 我们就实现了自定义事件发布和监听
 */
public class Main {

    static void test1() {

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        TaskComponent taskComponent = context.getBean(TaskComponent.class);
        taskComponent.task();

    }

    public static void main(String[] args) {

        test1();

    }

}

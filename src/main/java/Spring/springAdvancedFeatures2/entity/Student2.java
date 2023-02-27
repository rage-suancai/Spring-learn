package Spring.springAdvancedFeatures2.entity;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures2.entity
 * @ClassName: Student2
 * @Desription:
 * @date 2023/2/27 9:54
 */
@Component
public class Student2 {

    public void syncTest() {

        System.out.println(Thread.currentThread().getName() + "我是同步执行的方法 开始...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("我是同步执行的方法 结束");

    }

    @Async
    public void asyncTest() {

        System.out.println(Thread.currentThread().getName() + "我是异步执行的方法 开始...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("我是异步执行的方法 结束...");

    }

    @Scheduled(cron = "*/2 * * * * *")
    public void task() {
        System.out.println("我是定时任务!" + new Date());
    }

}

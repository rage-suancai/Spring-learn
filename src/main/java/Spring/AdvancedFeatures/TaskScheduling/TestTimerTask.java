package Spring.AdvancedFeatures.TaskScheduling;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class TestTimerTask {

    public void syncTest() throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + "我是同步执行的方法 开始...");
        Thread.sleep(3000);
        System.out.println("我是同步执行的方法 结束");

    }

    @Async
    public void asyncTest() throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + "我是异步执行的方法 开始...");
        Thread.sleep(3000);
        System.out.println("我是异步执行的方法 结束");

    }

    // @Scheduled(fixedRate = 2000)
    @Scheduled(cron = "*/2 * * * * *")
    public void task() {
        System.out.println("我是定时任务:" + new Date());
    }

}

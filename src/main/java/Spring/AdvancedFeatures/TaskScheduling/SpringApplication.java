package Spring.AdvancedFeatures.TaskScheduling;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApplication {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    public static void main(String[] args) {

        //test1();
        //test2();l
        test3();

    }

    static void test1() {

        TestTimerTask timerTask = (TestTimerTask) context.getBean("MyTimerTask");
        try {
            System.out.println("任务开始");
            //timerTask.syncTest();
            timerTask.asyncTest();
            System.out.println("任务结束");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static void test2() {

        TestTimerTask timerTask = (TestTimerTask) context.getBean("MyTimerTask");
        System.out.println(timerTask.getClass());

    }

    static void test3() {

        TestTimerTask timerTask = (TestTimerTask) context.getBean("MyTimerTask");
        timerTask.task();

    }

}

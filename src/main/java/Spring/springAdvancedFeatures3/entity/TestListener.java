package Spring.springAdvancedFeatures3.entity;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures3.entity
 * @ClassName: TestListener
 * @Desription:
 * @date 2023/2/27 11:59
 */
@Component
public class TestListener implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent event) {

        // System.out.println(event.getApplicationContext());
        System.out.println("发生了一次自定义事件 成功监听到");

    }

}

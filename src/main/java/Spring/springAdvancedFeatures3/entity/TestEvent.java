package Spring.springAdvancedFeatures3.entity;

import org.springframework.context.ApplicationEvent;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures3.entity
 * @ClassName: TestEvent
 * @Desription:
 * @date 2023/2/27 14:22
 */
public class TestEvent extends ApplicationEvent {

    public TestEvent(Object source) {
        super(source);
    }

}

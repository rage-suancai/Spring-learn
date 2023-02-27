package Spring.springAdvancedFeatures3.entity;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures3.entity
 * @ClassName: TaskComponent
 * @Desription:
 * @date 2023/2/27 14:54
 */
@Component
public class TaskComponent implements ApplicationEventPublisherAware {

    ApplicationEventPublisher publisher;

    @Scheduled(fixedRate = 1000)
    public void task() {

        publisher.publishEvent(new TestEvent(this));

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {

        this.publisher = publisher;

    }

}

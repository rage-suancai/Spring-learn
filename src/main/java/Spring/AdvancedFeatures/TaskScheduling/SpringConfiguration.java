package Spring.AdvancedFeatures.TaskScheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@Configuration
public class SpringConfiguration {

    @Bean(name = "MyTimerTask")
    public TestTimerTask task() {
        return new TestTimerTask();
    }

}

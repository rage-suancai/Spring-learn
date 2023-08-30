package Spring.AdvancedFeatures.Listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean(name = "MyListener")
    public ListenerTest listenerTest() {
        return new ListenerTest();
    }

}

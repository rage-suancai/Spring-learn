package Spring.AdvancedFeatures.Aware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean(name = "MyAware")
    public AwareTest awareTest() {
        return new AwareTest();
    }

}

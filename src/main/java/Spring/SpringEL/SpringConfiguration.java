package Spring.SpringEL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
public class SpringConfiguration {

    @Bean("MyEL")
    public ELTest elTest() {
        return new ELTest();
    }

}

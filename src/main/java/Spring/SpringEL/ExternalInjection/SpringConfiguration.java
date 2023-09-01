package Spring.SpringEL.ExternalInjection;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:application.properties")
@ComponentScan("Spring.SpringEL.ExternalInjection")
@Configuration
public class SpringConfiguration {

    /*@Bean("MyEL")
    public ELTest elTest() {
        return new ELTest();
    }*/

}

package Spring.SpringEL.EasyToUse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:application.properties")
@ComponentScan("Spring.SpringEL.EasyToUse")
@Configuration
public class SpringConfiguration {



}

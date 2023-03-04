package Spring.springEL1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author YXS
 * @PackageName: Spring.SpringEL1.config
 * @ClassName: MainConfiguration
 * @Desription:
 * @date 2023/2/27 16:12
 */
@Configuration
@ComponentScan("Spring.springEL1.entity") @PropertySource("classpath:SpringEL.properties")
public class MainConfiguration {



}

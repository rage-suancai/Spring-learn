package Spring.springAdvancedFeatures3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures2.config
 * @ClassName: mainConfiguration
 * @Desription:
 * @date 2023/2/27 11:40
 */
@EnableScheduling
@Configuration @ComponentScan("Spring.springAdvancedFeatures3.entity")
public class MainConfiguration {



}

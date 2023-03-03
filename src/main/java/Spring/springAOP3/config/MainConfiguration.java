package Spring.springAOP3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author YXS
 * @PackageName: Spring.springAOP3.config
 * @ClassName: MainConfiguration
 * @Desription:
 * @date 2023/3/3 1:49
 */
@EnableAspectJAutoProxy
@Configuration @ComponentScan("Spring.springAOP3.entity")
public class MainConfiguration {



}

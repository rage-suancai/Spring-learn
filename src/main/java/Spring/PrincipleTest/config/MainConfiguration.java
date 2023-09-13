package Spring.PrincipleTest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//@EnableAspectJAutoProxy
@ComponentScan("Spring.PrincipleTest.entity")
public class MainConfiguration {



}

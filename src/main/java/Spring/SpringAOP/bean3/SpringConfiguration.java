package Spring.SpringAOP.bean3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan("Spring.SpringAOP.bean3")
@Configuration
public class SpringConfiguration {

    public void study() {
        System.out.println();
    }

}

package Spring.spring7.config;

import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.Resources;
import java.sql.Connection;
import java.util.Date;


@EnableAspectJAutoProxy
@Configuration
@ComponentScans({
        @ComponentScan("Spring.spring7.Bean"),
        @ComponentScan("Spring.spring7.Aop")
})
@Import(Date.class)
public class MainConfiguration {

    /*@Resource
    Connection connection;
    @PostConstruct
    public void init(){
        System.out.println(connection);
    }*/

}

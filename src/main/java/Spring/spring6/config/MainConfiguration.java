package Spring.spring6.config;

import Spring.spring6.Bean.Card;
import Spring.spring6.Bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("Spring.spring6.Bean")
public class MainConfiguration {

}

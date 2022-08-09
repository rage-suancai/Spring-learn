package Spring.spring6.Bean;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@ToString
@Component
public class Student {
    int age;
    String name;
    @Resource
    Card card;

    @PostConstruct
    public void init(){
        System.out.println("属性card为: " + card);
    }

}

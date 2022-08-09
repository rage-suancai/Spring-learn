package Spring.spring5.Bean;

import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Component
public class Student {
    int age;
    String name;
    Card card;

    public void setName(String name) {
        this.name = name;
    }
}

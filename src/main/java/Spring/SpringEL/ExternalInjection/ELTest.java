package Spring.SpringEL.ExternalInjection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ELTest {

    /*@Value("${test.name}")
    private String name;*/

    private String name;

    public void test() {
        System.out.println("我叫: " + name);
    }

    public ELTest(@Value("${test.name}") String name) {
        this.name = name;
    }

}

package Spring.SpringEL;

import org.springframework.beans.factory.annotation.Value;

public class ELTest {

    @Value("${test.name}")
    private String name;

    public void test() {
        System.out.println("我叫: " + name);
    }

}

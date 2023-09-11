package Spring.Junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JunitTest.class)
public class JunitTest {

    @Test
    public void test() {
        System.out.println("我是测试");
    }

}

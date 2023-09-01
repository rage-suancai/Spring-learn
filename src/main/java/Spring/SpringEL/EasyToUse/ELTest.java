package Spring.SpringEL.EasyToUse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ELTest {

    private final String name;

    public ELTest(@Value("${test.name}") String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public Map<String,String> map = Map.of("test", "你干嘛");
    public List<String> list = List.of("AAA", "BBB", "CCC");

}

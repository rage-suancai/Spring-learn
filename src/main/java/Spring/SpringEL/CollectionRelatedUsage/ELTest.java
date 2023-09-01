package Spring.SpringEL.CollectionRelatedUsage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ELTest {

    /*public Map<String,String> map = Map.of("test", "你干嘛");
    public List<String> list = List.of("AAA", "BBB", "CCC");*/

    public List<Clazz> list = List.of(new Clazz("高等数学", 4));
    public record Clazz(String name, int score) { }

    private final String name;
    public ELTest(@Value("${test.name}") String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}

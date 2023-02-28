package Spring.SpringEL3.entity;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YXS
 * @PackageName: Spring.SpringEL3.entity
 * @ClassName: Student3
 * @Desription:
 * @date 2023/2/28 9:16
 */
@Component
public class Student3 {

    public String name;

    /*public Map<String, String> map = Map.of("yxs", "呼呼呼");
    public List<String> list = List.of("AAA", "BBB", "CCC");*/

    public List<Clazz> list = List.of(new Clazz("高等数学", "C语言程序设计"),
                                      new Clazz("中等数学", "Rust程序设计"),
                                      new Clazz("低等数学", "Java程序设计"));
    public record Clazz(String name, String score) { }


}

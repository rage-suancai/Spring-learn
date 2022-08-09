package Spring.spring4.Bean;

import lombok.ToString;

@ToString
public class Student {
    String name;
    int age;

    public Student(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String say(String text){
        System.out.println("我叫" + name + "今年" + age + "岁" + "我说: " + text);
        return text;
    }

}

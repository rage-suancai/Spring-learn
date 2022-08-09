package Spring.spring2.Bean;

import lombok.ToString;

@ToString
public class Student {
    String name;
    int age;
    Card card;

    public Student(String name){
        this.name = name;
    }
    public Student(String name, int age){
        this.age = age;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}

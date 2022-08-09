package Spring.spring7.Bean;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@ToString
@Component
public class Student {
    int age;
    String name;
    Card card;

    @Autowired
    public void setCard(Card card){
        this.card = card;
    }

    public String say(String text){
        System.out.println("我叫" + name + "今年" + age + "我的card属性为: " + card);
        return text;
    }

}

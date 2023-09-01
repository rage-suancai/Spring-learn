package Spring.SpringEL.CollectionRelatedUsage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpringApplication {

    static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

    static ExpressionParser parser = new SpelExpressionParser();

    public static void main(String[] args) {

        //test1();
        //test2();
        test3();

    }

    static void test1() {

        /*Expression exp = parser.parseExpression("map['test']");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));*/

        /*Expression exp = parser.parseExpression("list[2]");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));*/

        /*Expression exp = parser.parseExpression("list[2]");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));*/

        /*Expression exp = parser.parseExpression("{5, 2, 1, 4, 6, 7, 0, 3, 9, 8}");
        List value = (List) exp.getValue();
        value.forEach(System.out::println);*/

        /*Expression exp = parser.parseExpression("{{1, 2}, {3, 4}}");
        System.out.println(exp.getValue());*/

        Expression exp = parser.parseExpression("{name: '小明', info: {address: '北京市朝阳区', tel: 10086}}");
        System.out.println(exp.getValue());

    }

    static void test2() {

        /*Expression exp = parser.parseExpression("list.?[name == '高等数学']");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));*/

        /*Expression exp = parser.parseExpression("list.?[score > 5]");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));*/

        Expression exp = parser.parseExpression("list.![name]");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));

    }

    static void test3() {

        Expression exp = parser.parseExpression("name?.toUpperCase()");
        ELTest el = context.getBean(ELTest.class);
        System.out.println(exp.getValue(el));

    }

}

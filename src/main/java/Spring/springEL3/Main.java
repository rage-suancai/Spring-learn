package Spring.springEL3;

import Spring.springEL3.config.MainConfiguration;
import Spring.springEL3.entity.Student3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.List;

/**
 * 集合操作相关语法
 * 现在我们的类中存在一些集合类:
 *
 *                  @Component
 *                  public class Student3 {
 *
 *                      public Map<String, String> map = Map.of("yxs", "你干嘛");
 *                      public List<String> list = List.of("AAA", "BBB", "CCC");
 *
 *                  }
 *
 * 我们可以使用SpEL快速取出集合中的元素:
 *
 *                  Expression exp = parser.parseExpression("map['yxs']"); // 对于Map这里映射型 可以直接使用map[key]来取出value
 *                  System.out.println(exp.getValue(student3);
 *
 *                  Expression exp = parser.parseExpression("list[2]"); // 对于List 数组这类 可以直接使用[index]
 *                  System.out.println(exp.getValue(student3));
 *
 * 我们也可以快速创建集合:
 *
 *                  Expression exp = parser.parseExpression("{5, 2, 1, 4, 6, 7, 0, 3, 9, 8}"); // 使用{}来快速创建List集合
 *                  List value = (List) exp.getValue();
 *                  value.forEach(System.out::println);
 *
 *                  Expression exp = parser.parseExpression("{1, 2}, {3, 4}"); // 它是支持嵌套使用的
 *
 *                  // 创建Map也很简单 只需要key:value就可以了 怎么有股JSON味
 *                  Expression exp = parser.parseExpression("{name: '小明', info: {address: '北京朝阳区', tel: 10086}}");
 *                  System.out.println(exp.getValue());
 *
 * 你以为就这么简单吗 我们还可以直接根据条件获取集合中的元素:
 *
 *                  @Component
 *                  public class Student3 {
 *
 *                      public List<Clazz> list = List.of(new Clazz("高等数学", 4));
 *
 *                      public record Clazz(String name, int score) { }
 *
 *                  }
 *
 *                  // 现在我们希望从list中获取那些满足我们条件的元素 并组成一个新的集合 我们可以使用.?运算符
 *                  Expression exp = parser.parseExpression("list.?[name == '高等数学']");
 *                  System.out.println();
 *
 *                  Expression exp = parser.parseExpression("list.?[score > 3]"); // 选择学分大于3分的科目
 *                  System.out.println(exp.getValue(student3));
 *
 * 我们还可以针对于某个属性创建对应的投影集合:
 *
 *                  Expression exp = parser.parseExpression("list.![name]"); // 使用.!创建投影集合 这里创建的时课程名称组成的新集合
 *                  System.out.println(exp.getValue(student3));
 *
 *      https://img-blog.csdnimg.cn/img_convert/e4a75677d333178617e84451475b8a94.png
 *
 * 我们接着来介绍安全导航运算符 安全导航运算符用于避免NullPointerException 它来自Groovy语言 通常 当您有对对象的引用时
 * 您可能需要在访问对象的方法或属性之前验证它是否为空 为了避免这种情况 安全导航运算符返回null而不是抛出异常 以示例子显示了如何使用安全导航运算符:
 *
 *                  Expression exp = parser.parseExpression("name.toUpperCase()"); // 如果Student对象中的name属性为null
 *                  System.out.println(exp.getValue(student3));
 *
 *      https://img-blog.csdnimg.cn/img_convert/b52b98635ccac5c9d3276290a4a64930.png
 *
 * 当遇到null时很不方便 我们还得写判断:
 *
 *                  if(student.name != null) {
 *                      System.out.println(student.name.toUpperCase());
 *                  }
 *
 * Java8之后还能这样写:
 *
 *                  Optional.ofNullable(student.name).ifPresent(System.out::println);
 *
 * 但是你如果写过Kotlin:
 *
 *                  println(student.name?.toUpperCase());
 *
 * 类似这种判空问题 我们就可以直接使用安全导航运算符 SpEL也支持这种写法:
 *
 *                  Expression exp = parser.parseExpression("name?.toUpperCase()");
 *                  System.out.println(student3);
 *
 * 当遇到空时 只会得到一个null 而不是直接抛出一个异常:
 *
 *      https://img-blog.csdnimg.cn/img_convert/c54edd42ce99ff7ba52b9d23896290f0.png
 *
 * 我们可以将SpEL配合@Value注解或是xml配置文件中的value属性使用 比如XML中可以这样写:
 *
 *                  <bean id="numberGuess" class="Spring.SpringEL3.entity。Student3">
 *                      <property name="randomNumber" value="#{ T(java.lang.Math).random() * 100.0 }"/>
 *                  </bean>
 *
 * 或是使用注解开发:
 *
 *                  public class FieldValueTestBean {
 *
 *                      @Value("#{ systemProperties['user.region']")
 *                      private String defaultLocale;
 *
 *                  }
 *
 * 这样 我们有时候在使用配置文件中的值时 就能进行一些简单的处理了
 *
 * 有关更多详细语法教程 请前往: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions-language-ref
 */
public class Main {

    static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
    static ExpressionParser parser = new SpelExpressionParser();

    static Student3 student3 = context.getBean(Student3.class);

    static void test1() {

        /*Expression exp = parser.parseExpression("map['yxs']");
        System.out.println(exp.getValue(student3));*/

        /*Expression exp = parser.parseExpression("list[2]");
        System.out.println(exp.getValue(student3));*/

        Expression exp = parser.parseExpression("{5, 2, 1, 4, 6, 7, 0, 3, 9, 8}");
        List value = (List) exp.getValue();
        value.forEach(System.out::print);

    }

    static void test2() {

        /*Expression exp = parser.parseExpression("list.?[name == '高等数学']");
        System.out.println(exp.getValue(student3));*/

        /*Expression exp = parser.parseExpression("list.?[score > 3]");
        System.out.println(exp.getValue(student3));*/

        Expression exp = parser.parseExpression("list.![score]");
        System.out.println(exp.getValue(student3));

    }

    static void test3() {

        /*Expression exp = parser.parseExpression("name.toUpperCase()");
        System.out.println(exp.getValue(student3));*/

        /*if (student3.name != null) {
            System.out.println(student3.name.toUpperCase());
        }*/

        //Optional.ofNullable(student3.name).ifPresent(System.out::println);

        Expression exp = parser.parseExpression("name?.toUpperCase");
        System.out.println(exp.getValue(student3));

    }

    public static void main(String[] args) {

        //test1();
        //test2();
        test3();

    }

}

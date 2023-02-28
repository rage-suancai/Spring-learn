package Spring.SpringEL2;

import Spring.SpringEL2.config.MainConfiguration;
import Spring.SpringEL2.entity.Student2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * SpEL简单使用
 * Spring官方为我们提供了一套非常高级SpEL表达式 通过使用表达式我们可以更加灵活地使用Spring框架
 *
 * 首先我们来看看如何创建一个SpEL表达式:
 *
 *                  ExpressionParser parser = new SpelExpressionParser();
 *                  Expression exp = parser.parseExpression("'Fuck World'"); // 使用parseExpression方法来创建一个表达式
 *                  System.out.println(exp.getValue()); // 表达式最终运结果可以通过getValue()获取
 *
 * 这里得到的就是一个很简单的Hello World字符串 字符串使用单引号囊括 SpEL是具有运算能力的
 *
 * 我们可以像写Java一样 对这个字符串进行各种操作 比如调用方法之类的:
 *
 *                  Expression exp = parser.parseExpression("'Fuck World'.toUpperCase"); // 调用String的toUpperCase方法
 *                  System.out.println(exp.getValue());
 *
 *                  FUCK WORLD
 *
 * 不仅能调用方法 还可以访问属性 使用构造方法等 是不是感觉挺牛的 居然还能这样玩
 *
 * 对于Getter方法 我们可以像访问属性一样去使用:
 *
 *                  // 比如 String.getBytes()方法 就是一个Getter 那么可以写成bytes
 *                  Expression exp = parseExpression("'Fuck World'.bytes");
 *                  System.out.println(exp.getValue());
 *
 * 表达式可以不止一级 我们可以多级调用:
 *
 *                  Expression exp = parser.parseExpression("'Fuck World'.bytes.length"); // 继续访问数组的length属性
 *                  System.out.println(exp.getValue());
 *
 * 是不是感觉挺好玩的? 我们继续来试试看构造方法 其实就是写Java代码 只是可以写成这种表达式而已:
 *
 *                  Expression exp = parser.parseExpression("new String('Fuck World').toUpperCase()");
 *                  System.out.println(exp.getValue());
 *
 *                  FUCK WORLD
 *
 * 它甚至还支持根据特定表达式 从给定对象中获取属性出来:
 *
 *                  @Component
 *                  public class Student2 {
 *
 *                      private String name;
 *
 *                      public Student(@Value("${Student2}")) {
 *                          this.name = name;
 *                      }
 *
 *                      public String getName() { // 比如下面要访问name属性 那么这个属性得可以访问才行 访问权限不够是不行的
 *                          return name;
 *                      }
 *
 *                  }
 *
 *                  Student2 student2 = context.getBean(Student2.class);
 *                  ExpressionParser parser = new SpelExpressionParser();
 *                  Expression exp = parser.parseExpression("name");
 *                  System.out.println(exp.getValue(student2)); // 直接读取对象的name属性
 *
 *
 * 拿到对象属性之后 甚至还可以继续去处理:
 *
 *                  Expression exp = parser.parseExpression(); // 拿到name之后继续getBytes然后length
 *
 * 除了获取 我们也可以调用表达式的setValue方法来设定属性的值:
 *
 *                  Expression exp = parser.parseExpression("name");
 *                  exp.setValue(student2, "yxs"); // 同样的 这个属性得有访问权限且能set才可以 否则会报错
 *
 * 除了属性调用 我们也可以使用运算符进行各种高级运算:
 *
 *                  Expression exp = parser.parseExpression("66 > 77"); // 比较运算
 *                  System.out.println(exp.getValue());
 *
 *                  Expression exp = parser.parseExpression("99 + 99 * 3"); // 算数运算
 *                  System.out.println(exp.getValue());
 *
 * 对于那些需要导入才能使用的类 我们需要使用一个特殊的语法:
 *
 *                  Expression exp = parser.parseExpression("T(java.lang.Math).random()"); // 由T()囊括 包含完整包名+类名
 *                  // Expression exp = parser.parseExpression("T(System).nanoTime()"); // 默认导入的类可以不加包名
 *                  System.out.println(exp.getValue);
 */
public class Main {

    static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
    static ExpressionParser parser = new SpelExpressionParser();

    static void test1() {

        /*Expression exp = parser.parseExpression("'Fuck World'");
        System.out.println(exp.getValue());*/

        /*Expression exp = parser.parseExpression("'Fuck World'.toUpperCase()");
        System.out.println(exp.getValue());*/

        /*Expression exp = parser.parseExpression("'Fuck World'. bytes");
        System.out.println(exp.getValue());*/

        /*Expression exp = parser.parseExpression("'Fuck World'. bytes.length");
        System.out.println(exp.getValue());*/

        Expression exp = parser.parseExpression("new String('Fuck World').toUpperCase()");
        System.out.println(exp.getValue());

    }

    static void test2() {

        Student2 student2 = context.getBean(Student2.class);

        /*Expression exp = parser.parseExpression("name");
        System.out.println(exp.getValue(student2));*/

        /*Expression exp = parser.parseExpression("name.bytes.length");
        System.out.println(exp.getValue(student2));*/

        /*Expression exp = parser.parseExpression("name");
        System.out.println(exp.getValue(student2));*/

        /*Expression exp = parser.parseExpression("66 > 99");
        System.out.println(exp.getValue());*/

        /*Expression exp = parser.parseExpression("99 + 99 * 3");
        System.out.println(exp.getValue());*/

        Expression exp1 = parser.parseExpression("T(java.lang.Math).random()");
        Expression exp2 = parser.parseExpression("T(System).nanoTime");
        System.out.println(exp1.getValue());
        System.out.println(exp2.getValue());

    }

    public static void main(String[] args) {

        //test1();
        test2();

    }

}

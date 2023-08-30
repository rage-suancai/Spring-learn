### SpringEL表达式
SpringEL是一种强大 简洁的装配Bean的方式 它可以通过运行期间执行的表达式将值装配到我们的属性或构造函数当中 更好可以调用JDK中提供的静态常量 获取外部Properties文件中的配置

### 外部属性注入
有些时候 我们甚至可以将一些外部配置文件中的配置进行读取 并完成注入

我们需要创建以.properties结尾的配置文件 这种配置文件格式很简单 类似于Map 需要一个key和一个Value 中间使用等号进行连接 这里我们在resource目录下创建一个test.properties文件:

```properties
                    test.name=只因
```

这样 key就是test.name Value就是只因 我们可以通过一个注解直接读取到外部配置文件中对应的属性值 首先我们需要引入这个配置文件 我们可以在配置类上添加@PropertySource注解:

```java
                    @Configuration
                    @ComponentScan("com.test.bean")
                    @PropertySource("classpath:test.properties") // 注意 类路径下的文件名称需要在前面加上classpath:
                    public class MainConfiguration {
                        
                    }
```

接着 我们就可以开始快乐的使用了 我们可以使用@Value注解将外部配置文件中的值注入到任何我们想要的位置 就像我们之前使用@Resource自动注入一样

```java
                    @Component
                    public class Student {
                    
                        @Value("${test.name}") // 这里需要在外层套上 ${ }
                        private String name; // String会被自动赋值为配置文件中对应属性的值
                    
                        public void hello() {
                            System.out.println("我的名字是: " + name);
                        }
                        
                    }
```

@Value中的${...}表示占位符 它会读取外部配置文件的属性值装配到属性中 如果配置正确没问题的话 这里甚至还会直接显示对应配置项的值:

<img src="https://image.itbaima.net/markdown/2022/11/25/HDZ4l3tcreoOGh8.png"/>

我们来测试一下吧:

<img src="https://image.itbaima.net/markdown/2022/11/25/g5tBKW4Sm9lXnrR.png"/>

如果遇到乱码的情况 请将配置文件的编码格式切换成UTF-8(可以在IDEA设置中进行配置)然后在@PropertySource注解中添加属性
encoding = "UTF-8" 这样就正常了 当然 其实一般情况下也很少会在配置文件中用到中文

除了在字段上进行注入之外 我们也可以在需要注入的方法中使用:

```java
                    @Component
                    public class Student {
                    
                        private final String name;
                    
                      	// 构造方法中的参数除了被自动注入外 我们也可以选择使用@Value进行注入
                        public Student(@Value("${test.name}") String name) {
                            this.name = name;
                        }
                    
                        public void hello() {
                            System.out.println("我的名字是: " + name);
                        }
                        
                    }
```

当然 如果我们只是简单的注入一个常量值 也可以直接填入固定值:

```java
                    private final String name;
                    public Student(@Value("10") String name) { // 只不过 这里都是常量值了 我干嘛不直接写到代码里呢
                        this.name = name;
                    }
```

当然 @Value的功能还远不止这些 配合SpringEL表达式 能够实现更加强大的功能

### SpringEL简单使用
Spring官方为我们提供了一套非常高级SpringEL表达式 通过使用表达式 我们可以更加灵活地使用Spring框架

首先我们来看看如何创建一个SpringEL表达式:

```java
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression("'Hello World'"); // 使用parseExpression方法来创建一个表达式
                    System.out.println(exp.getValue()); // 表达式最终的运算结果可以通过getValue()获取
```

这里得到的就是一个很简单的Hello World字符串 字符串使用单引号囊括 SpringEL是具有运算能力的

我们可以像写Java一样 对这个字符串进行各种操作 比如调用方法之类的:

```java
                    Expression exp = parser.parseExpression("'Hello World'.toUpperCase()"); // 调用String的toUpperCase方法
                    System.out.println(exp.getValue());
```

<img src="https://image.itbaima.net/markdown/2022/11/25/PZmheYn5EVTvURN.png"/>

不仅能调用方法, 还可以访问属性, 使用构造方法等 是不是感觉挺牛的 居然还能这样玩

对于Getter方法 我们可以像访问属性一样去使用:

```java
                    // 比如String.getBytes()方法 就是一个Getter 那么可以写成bytes
                    Expression exp = parser.parseExpression("'Hello World'.bytes");
                    System.out.println(exp.getValue());
```

表达式可以不止一级 我们可以多级调用:

```java
                    Expression exp = parser.parseExpression("'Hello World'.bytes.length"); // 继续访问数组的length属性
                    System.out.println(exp.getValue());
```

是不是感觉挺好玩的? 我们继续来试试看构造方法 其实就是写Java代码 只是可以写成这种表达式而已:

```java
                    Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
                    System.out.println(exp.getValue());
```

<img src="https://image.itbaima.net/markdown/2022/11/25/PZmheYn5EVTvURN.png"/>

它甚至还支持根据特定表达式 从给定对象中获取属性出来:

```java
                    @Component
                    public class Student {
    
                        private final String name;
                        
                        public Student(@Value("${test.name}") String name){
                            this.name = name;
                        }
                    
                        public String getName() { // 比如下面要访问name属性 那么这个属性得可以访问才行 访问权限不够是不行的
                            return name;
                        }
                        
                    }
```
```java
                    Student student = context.getBean(Student.class);
                    ExpressionParser parser = new SpelExpressionParser();
                    Expression exp = parser.parseExpression("name");
                    System.out.println(exp.getValue(student)); // 直接读取对象的name属性
```

拿到对象属性之后 甚至还可以继续去处理:

```java
                    Expression exp = parser.parseExpression("name.bytes.length"); // 拿到name之后继续getBytes然后length
```

除了获取 我们也可以调用表达式的setValue方法来设定属性的值:

```java
                    Expression exp = parser.parseExpression("name");
                    exp.setValue(student, "刻师傅"); // 同样的 这个属性得有访问权限且能set才可以 否则会报错
```

除了属性调用 我们也可以使用运算符进行各种高级运算:

```java
                    Expression exp = parser.parseExpression("66 > 77"); // 比较运算
                    System.out.println(exp.getValue());
```
```java
                    Expression exp = parser.parseExpression("99 + 99 * 3"); // 算数运算
                    System.out.println(exp.getValue());
```

对于那些需要导入才能使用的类 我们需要使用一个特殊的语法:

```java
                    Expression exp = parser.parseExpression("T(java.lang.Math).random()"); // 由T()囊括 包含完整包名+类名
                    // Expression exp = parser.parseExpression("T(System).nanoTime()"); // 默认导入的类可以不加包名
                    System.out.println(exp.getValue());
```

### 集合操作相关语法
现在我们的类中存在一些集合类:

```java
                    @Component
                    public class Student {
                    
                        public Map<String, String> map = Map.of("test", "你干嘛");
                        public List<String> list = List.of("AAA", "BBB", "CCC");
                        
                    }
```

我们可以使用SpringEL快速取出集合中的元素:

```java
                    Expression exp = parser.parseExpression("map['test']"); // 对于Map这里映射型 可以直接使用map[key]来取出value
                    System.out.println(exp.getValue(student));
```
```java
                    Expression exp = parser.parseExpression("list[2]"); // 对于List, 数组这类 可以直接使用[index]
                    System.out.println(exp.getValue(student));
```

我们也可以快速创建集合:

```java
                    Expression exp = parser.parseExpression("{5, 2, 1, 4, 6, 7, 0, 3, 9, 8}"); // 使用{}来快速创建List集合
                    List value = (List) exp.getValue();
                    value.forEach(System.out::println);
```
```java
                    Expression exp = parser.parseExpression("{{1, 2}, {3, 4}}"); // 它是支持嵌套使用的
```
```java
                    // 创建Map也很简单 只需要key:value就可以了 怎么有股JSON味
                    Expression exp = parser.parseExpression("{name: '小明', info: {address: '北京市朝阳区', tel: 10086}}");
                    System.out.println(exp.getValue());
```

你以为就这么简单吗 我们还可以直接根据条件获取集合中的元素:

```java
                    @Component
                    public class Student {
                    
                        public List<Clazz> list = List.of(new Clazz("高等数学", 4));
                    
                        public record Clazz(String name, int score) {}
                        
                    }
```
```java
                    // 现在我们希望从list中获取那些满足我们条件的元素 并组成一个新的集合 我们可以使用.?运算符
                    Expression exp = parser.parseExpression("list.?[name == '高等数学']");
                    System.out.println(exp.getValue(student));
```
```java
                    Expression exp = parser.parseExpression("list.?[score > 3]"); // 选择学分大于3分的科目
                    System.out.println(exp.getValue(student));
```

我们还可以针对某个属性创建对应的投影集合:

```java
                    Expression exp = parser.parseExpression("list.![name]"); // 使用.!创建投影集合 这里创建的时课程名称组成的新集合
                    System.out.println(exp.getValue(student));
```

<img src="https://image.itbaima.net/markdown/2022/11/30/yLNHPJnWkoR3Cb2.png"/>

我们接着来介绍安全导航运算符 安全导航运算符用于避免NullPointerException 它来自Groovy语言 通常 当您有对对象的引用时
您可能需要在访问对象的方法或属性之前验证它是否为空 为了避免这种情况 安全导航运算符返回null而不是抛出异常 以下示例显示了如何使用安全导航运算符:

```java
                    Expression exp = parser.parseExpression("name.toUpperCase()"); // 如果Student对象中的name属性为null
                    System.out.println(exp.getValue(student));
```

<img src="https://image.itbaima.net/markdown/2022/11/30/dojeP5kYcM7KHiv.png"/>

当遇到null时很不方便 我们还得写判断:

```java
                    if(student.name != null)
                        System.out.println(student.name.toUpperCase());
```

Java8之后能这样写:

```java
                    Optional.ofNullable(student.name).ifPresent(System.out::println);
```

但是你如果写过Kotlin:

```java
                    println(student.name?.toUpperCase());
```

类似于这种判空问题 我们就可以直接使用安全导航运算符 SpringEL也支持这种写法:

```java
                    Expression exp = parser.parseExpression("name?.toUpperCase()");
                    System.out.println(exp.getValue(student));
```

当遇到空时 只会得到一个null 而不是直接抛出一个异常:

<img src="https://image.itbaima.net/markdown/2022/11/30/tOf3LFsWE4H8BVc.png"/>

我们可以将SpringEL配合@Value注解或是xml配置文件中的value属性使用 比如XML中可以这样写:

```java
                    <bean id="numberGuess" class="org.spring.samples.NumberGuess">
                        <property name="randomNumber" value="#{ T(java.lang.Math).random() * 100.0 }"/>
                    </bean>
```

或是使用注解开发:

```java
                    public class FieldValueTestBean {
                        @Value("#{ systemProperties['user.region'] }")
                        private String defaultLocale;
                    }
```

这样 我们有时候在使用配置文件中的值时 就能进行一些简单的处理了

有关更多详细语法教程 请前往: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions-language-ref
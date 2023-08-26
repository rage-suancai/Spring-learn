<img src="https://image.itbaima.net/markdown/2022/10/08/ZsKlOvz5xmXSutw.png"/>

### Spring核心技术
前置课程要求: 请各位小伙伴先完成《JavaWeb》篇,《Java 9-17新特性》篇文章之后 再来观看此篇文章

建议: 对Java开发还不是很熟悉的同学 最好先花费半个月到一个月时间大量地去编写小项目 不推荐一口气学完
后面的内容相比前面的内容几乎是降维打击 一口气学完很容易忘记之前所学的基础知识 尤其是JavaSE阶段的内容

不同于2021版本SSM篇文章 本期文章为重制版本 `文章学习的Spring框架版本为: 6.0`

恭喜各位顺利进入到SSM(Spring+SpringMVC+Mybatis)阶段的学习 也算是成功出了Java新手村 由于前面我们已经学习过Mybatis了 因此 本期文章的时间安排相比之前会更短一些
从这里开始 很多的概念理解起来就稍微有一点难度了 因为你们没有接触过企业开发场景 很难体会到那种思想带来的好处
甚至到后期接触到的几乎都是基于云计算和大数据理论实现的框架(当下最热门最前沿的技术)逐渐不再是和计算机基础相关联 而是和怎么高效干活相关了

在JavaWeb阶段 我们已经学习了如何使用Java进行Web应用程序开发 我们现在已经具有搭建Web网站的能力 但是 我们在开发的过程中 发现存在诸多的不便 在最后的图书管理系统编程实战中
我们发现虽然我们思路很清晰 知道如何编写对应的接口 但是这样的开发效率 实在是太慢了 并且对于对象创建的管理 存在诸多的不妥之处 因此 我们要去继续学习更多的框架技术 来简化和规范我们的Java开发

Spring就是这样的一个框架(文档: https://docs.spring.io/spring-framework/docs/6.0.10/reference/html/core.html#spring-core) 它就是为了简化开发而生
它是轻量级的IoC和AOP的容器框架 主要是针对Bean的生命周期进行管理的轻量级容器 并且它的生态已经发展得极为庞大 那么 首先一问 什么是IoC和AOP 什么又是Bean呢? 不要害怕
这些概念只是听起来满满的高级感 实际上没有多高级(很多东西都是这样 名字听起来很牛 实际上只是一个很容易理解的东西)

### IoC容器基础
Spring框架最核心的其实是它的IoC容器 这是我们开启Spring学习的第一站

#### IoC理论介绍
在我们之前的图书管理系统Web应用程序中 我们发现 整个程序其实是依靠各个部分相互协作 共同完成一个操作 比如要展示借阅信息列表
那么首先需要使用Servlet进行请求和响应的数据处理 然后请求的数据全部交给对应的Service(业务层)来处理 当Service发现要从数据库中获取数据时 再向对应的Mapper发起请求

它们之间就像连接在一起的齿轮 谁也离不开谁:

<img src="https://image.itbaima.net/markdown/2022/10/08/YQRP2idIS5skHJ4.png"/>

就像一个团队 每个人的分工都很明确 流水线上的一套操作必须环环相扣 这是一种高度耦合的体系

虽然这样的体系逻辑非常清晰 整个流程也能够让人快速了解 但是这样存在一个很严重的问题 我们现在的时代实际上是一个软件项目高速迭代的时代
我们发现很多App三天两头隔三差五地就更新 而且是什么功能当下最火 就马不停蹄地进行跟进开发 因此 就很容易出现 之前写好的代码
实现的功能 需要全部推翻 改成新的功能 那么我们就不得不去修改某些流水线上的模块 但是这样一修改 会直接导致整个流水线的引用关系大面积更新

比如下面的情况:

```java
                   class A {
                        
                        private List<B> list;
                        
                        public B test(B b) {
                            return null;
                        }
    
                   }
                   
                   class C {
                        
                        public C(B b) { }
    
                   }
                   
                   class B { }
```

可以看到 A和C在大量地直接使用B 但是某一天 这个B的实现已经过时了 此时来了个把功能实现的更好的D 我们需要用这个新的类来完成业务了:

<img src="https://image.itbaima.net/markdown/2022/11/22/FRQn6vEpTklsJKe.png"/>

可以看到 因为类之间的关联性太强了 会开始大面积报错 所有之前用了B的类 得挨个进行修改 全都改成D 这简直是灾难啊

包括我们之前JavaWeb阶段编写的实战项目 如果我们不想用某个Service实现类了 我想使用其他的实现类用不同的逻辑做这些功能
那么这个时候 我们只能每个类都去挨个进行修改 当项目特别庞大时 光是改个类名导致的连带修改就够你改一天了

因此 高耦合度带来的缺点是很明显的 也是现代软件开发中很致命的问题 如果要改善这种情况 我们只能将各个模块进行解耦 让各个模块之间的依赖性不再那么地强 也就是说
Service的实现类 不再由我们决定 而是让程序自己决定 所有的实现类对象 全部交给程序来管理 所有对象之间的关系 也由程序来动态决定 这样就引入了IoC理论

IOC是Inversion of Control的缩写 翻译为: “控制反转” 把复杂系统分解成相互合作的对象 这些对象类通过封装以后 内部实现对外部是透明的 从而降低了解决问题的复杂度 而且可以灵活地被重用和扩展

<img src="https://image.itbaima.net/markdown/2022/10/08/XsYQRk93CHewISB.png"/>

我们可以将对象交给IoC容器进行管理 比如当我们需要一个接口的实现时 由它根据配置文件来决定到底给我们哪一个实现类 这样 我们就可以不用再关心我们要去使用哪一个实现类了 我们只需要关心
给到我的一定是一个可以正常使用的实现类 能用就完事了 反正接口定义了啥 我只管调 这样 我们就可以放心地让一个人去写视图层的代码 一个人去写业务层的代码 开发效率那是高的一匹啊

还是之前的代码 但是有了IoC容器加持之后:

```java
                    public static void main(String[] args) {
    
                        A a = new A();
                      	a.test(IoC.getBean(Service.class)); // 瞎编的一个容器类 但是是那个意思
                      	// 比如现在在IoC容器中管理的Service的实现是B 那么我们从里面拿到的Service实现就是B
        
                    }
                    
                    class A {
    
                        private List<Service> list; // 一律使用Service 具体实现由IoC容器提供
                        
                        public Service test(Service b){
                            return null;
                        }
                        
                    }
                    
                    interface Service{ } // 使用Service做一个顶层抽象
                    
                    class B implements Service{} // B依然是具体实现类 并交给IoC容器管理
```

当具体实现类发生修改时 我们同样只需要将新的实现类交给IoC容器管理 这样我们无需修改之前的任何代码:

```java
                    interface Service { }

                    class D implements Service { } // 现在实现类变成了D 但是之前的代码并不会报错
```

这样 即使我们的底层实现类发生了修改 也不会导致与其相关联的类出现错误 而进行大面积修改 通过定义抽象+容器管理的形式 我们就可以将原有的强关联解除

高内聚 低耦合 是现代软件的开发的设计目标 而Spring框架就给我们提供了这样的一个IoC容器进行对象的的管理 一个由Spring IoC容器实例化, 组装和管理的对象 我们称其为Bean

### 第一个Spring项目
首先一定要明确 使用Spring首要目的是为了使得软件项目进行解耦 而不是为了去简化代码! 通过它 就可以更好的对我们的Bean进行管理 这一部分我们来体验一下Spring的基本使用

Spring并不是一个独立的框架 它实际上包含了很多的模块:

<img src="https://image.itbaima.net/markdown/2022/11/21/KT2XhuCNVmcSvi5.png"/>

而我们首先要去学习的就是Core Container 也就是核心容器模块 只有了解了Spring的核心技术 我们才能真正认识这个框架为我们带来的便捷之处

Spring是一个非入侵式的框架 就像一个工具库一样 它可以很简单地加入到我们已有的项目中 因此 我们只需要直接导入其依赖就可以使用了 Spring核心框架的Maven依赖坐标

```xml
                    <dependency>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-context</artifactId>
                        <version>6.0.10</version>
                    </dependency>
```

注意: 与旧版教程不同的是 `Spring6要求你使用的Java版本为17及以上 包括后面我们在学习SpringMvc时 要求Tomcat版本必须为10以上` 这个依赖中包含了如下依赖:

<img src="https://image.itbaima.net/markdown/2022/11/22/HszTflPavUdQKGJ.png"/>

这里出现的都是Spring核心相关的内容 如Beans, Core, Context, SpEL以及非常关键的AOP框架 在本章中 我们都会进行讲解

    如果在使用Spring框架的过程中出现如下警告:
    
                        12月 17, 2022 3:26:26 下午 org.springframework.core.LocalVariableTableParameterNameDiscoverer inspectClass
                        警告: Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: XXXX

    这是因为LocalVariableTableParameterNameDiscoverer在Spring6.0.1版本已经被标记为过时 并且即将移除 请在Maven配置文件中为编译插件添加-parameters编译参数:

                        <build>
                            <pluginManagement>
                                <plugins>
                                    <plugin>
                                        <artifactId>maven-compiler-plugin</artifactId>
                                        <version>3.10.1</version>
                                        <configuration>
                                            <compilerArgs>
                                                <arg>-parameters</arg>
                                            </compilerArgs>
                                        </configuration>
                                    </plugin>
                                </plugins>
                            </pluginManagement>
                        </build>

    没有此问题请无视这部分

这里我们就来尝试编写一个最简单的Spring项目 我们在前面已经讲过了 Spring会给我们提供IoC容器用于管理Bean 但是我们得先为这个容器编写一个配置文件 我们可以通过配置文件告诉容器需要管理哪些Bean以及Bean的属性, 依赖关系等等

首先我们需要在resource中创建一个Spring配置文件(在resource中创建的文件 会在编译时被一起放到类路径下) 命名为test.xml 直接右键点击即可创建:

```xml
                    <?xml version="1.0" encoding="UTF-8"?>
                    <beans xmlns="http://www.springframework.org/schema/beans"
                           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xsi:schemaLocation="http://www.springframework.org/schema/beans
                            https://www.springframework.org/schema/beans/spring-beans.xsd">
    
                    </beans>
```

此时IDEA会提示我们没有为此文件配置应用程序上下文 这里我们只需要指定成当前项目就行了 当然配置这个只是为了代码提示和依赖关系快速查看 如果不进行配置也不会影响什么 程序依然可以正常运行:

<img src="https://image.itbaima.net/markdown/2022/11/21/bBtrSWlVz9oD2JF.png"/>

这里我们直接按照默认配置点确定就行了:

<img src="https://image.itbaima.net/markdown/2022/11/21/xoatfu4nX6iRr3v.png"/>

Spring为我们提供了一个IoC容器 用于去存放我们需要使用的对象 我们可以将对象交给IoC容器进行管理 当我们需要使用对象时 就可以向IoC容器去索要
并由它来决定给我们哪一个对象 而我们如果需要使用Spring为我们提供的IoC容器 那么就需要创建一个应用程序上下文 它代表的就是IoC容器 它会负责实例化, 配置和组装Bean

```java
                    public static void main(String[] args) {
    
                      	// ApplicationContext是应用程序上下文的顶层接口 它有很多种实现 这里我们先介绍第一种
                      	// 因为这里使用的是XML配置文件 所以说我们就使用 ClassPathXmlApplicationContext 这个实现类
                        ApplicationContext context = new ClassPathXmlApplicationContext("test.xml"); // 这里写上刚刚的名字
        
                    }
```

比如现在我们要让IoC容器帮助我们管理一个Student对象(Bean) 当我们需要这个对象时再申请 那么就需要这样 首先先将Student类定义出来

```java
                    package com.test.bean;

                    public class Student {
                        
                        public void hello(){
                            System.out.println("Hello World!");
                        }
                        
                    }
```

既然现在要让别人帮忙管理对象 那么就不能再由我们自己去new这个对象了 而是编写对应的配置 我们打开刚刚创建的test.xml文件进行编辑 添加:

```xml
                    <bean name="student" class="com.test.bean.Student"/>
```

这里我们就在配置文件中编写好了对应Bean的信息 之后容器就会根据这里的配置进行处理了

现在 这个对象不需要我们再去创建了 而是由IoC容器自动进行创建并提供 我们可以直接从上下文中获取到它为我们创建的对象:

```java
                    public static void main(String[] args) {
    
                        ApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
                        Student student = (Student) context.getBean("student"); // 使用getBean方法来获取对应的对象(Bean)
                        student.hello();
                        
                    }
```

实际上 这里得到的Student对象是由Spring通过反射机制帮助我们创建的 初学者会非常疑惑 为什么要这样来创建对象 我们直接new一个它不香吗? 为什么要交给IoC容器管理呢? 在后面的学习中 我们再慢慢进行体会

<img src="https://image.itbaima.net/markdown/2022/11/22/sjLiFokU1f3CvH5.png"/>

### Bean注册与配置
前面我们通过一个简单例子体验了一下如何使用Spring来管理我们的对象 并向IoC容器索要被管理的对象 这节我们就来详细了解一下如何向Spring注册Bean以及Bean的相关配置

实际上我们的配置文件可以有很多个 并且这些配置文件是可以相互导入的:

```xml
                    <?xml version="1.0" encoding="UTF-8"?>
                    <beans ...>
                        <import resource="test.xml"/>
                    </beans>
```

但是为了简单起见 我们还是从单配置文件开始讲起 首先我们需要知道如何配置Bean并注册

要配置一个Bean 只需要添加:

```xml
                    <bean/>
```

但是这样写的话 Spring无法得知我们要配置的Bean到底是哪一个类 所以说我们还得指定对应的类才可以:

```xml
                    <bean class="com.test.bean.Student"/>
```

<img src="https://image.itbaima.net/markdown/2022/11/22/SRV3znQJH4A7vDl.png"/>

可以看到类的旁边出现了Bean的图标 表示我们的Bean已经注册成功了 这样 我们就可以根据类型向容器索要Bean实例对象了:

```java
                    public static void main(String[] args) {
    
                        ApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
                      	// getBean有多种形式 其中第一种就是根据类型获取对应的Bean
                      	// 容器中只要注册了对应类的Bean或是对应类型子类的Bean 都可以获取到
                        Student student = context.getBean(Student.class);
                        student.hello();
                        
                    }
```

不过在有些时候 Bean的获取可能会出现歧义 我们可以来分别注册两个子类的Bean:

```java
                    public class ArtStudent extends Student{
    
                      	public void art() {
                            System.out.println("我爱画画");
                        }
                        
                    }
```
```java
                    public class SportStudent extends Student{
    
                        public void sport() {
                            System.out.println("我爱运动");
                        }
                        
                    }
```
```java
                    <bean class="com.test.bean.ArtStudent"/>
                    <bean class="com.test.bean.SportStudent"/>
```

但是此时我们在获取Bean时却是索要的它们的父类:

```java
                    Student student = context.getBean(Student.class);
                    student.hello();
```

运行时得到如下报错:

<img src="https://image.itbaima.net/markdown/2022/11/22/vliWJ4SZdx5E6yX.png"/>

这里出现了一个Bean定义不唯一异常 很明显 因为我们需要的类型是Student 但是此时有两个Bean定义都满足这个类型 它们都是Student的子类 此时IoC容器不知道给我们返回哪一个Bean 所以就只能抛出异常了

因此 如果我们需要一个Bean并且使用类型进行获取 那么必须要指明类型并且不能出现歧义:

```java
                    ArtStudent student = context.getBean(ArtStudent.class);
                    student.art();
```

那要是两个Bean的类型都是一样的呢?

```xml
                    <bean class="com.test.bean.Student"/>
                    <bean class="com.test.bean.Student"/>
```

这种情况下 就无法使用Class来进行区分了 除了为Bean指定对应类型之外 我们也可以为Bean指定一个名称用于区分:

```xml
                    <bean name="art" class="com.test.bean.ArtStudent"/>
                    <bean name="sport" class="com.test.bean.SportStudent"/>
```

name属性就是为这个Bean设定一个独一无二的名称(id属性也可以 跟name功能相同 但是会检查命名是否规范 否则会显示黄标) 不同的Bean名字不能相同 否则报错:

```xml
                    <bean name="a" class="com.test.bean.Student"/>
                    <bean name="b" class="com.test.bean.Student"/>
```

这样 这两个Bean我们就可以区分出来了:

```java
                    Student student = (Student) context.getBean("a");
                    student.hello();
```

虽然目前这两Bean定义都是一模一样的 也没什么区别 但是这确实是两个不同的Bean 只是类型一样而已 之后我们还可以为这两个Bean分别设置不同的其他属性

我们可以给Bean起名字 也可以起别名 就行我们除了有一个名字之外 可能在家里还有自己的小名:

```xml
                    <bean name="a" class="com.test.bean.Student"/>
                    <alias name="a" alias="test"/>
```

这样 我们使用别名也是可以拿到对应的Bean的:

```java
                    Student student = (Student) context.getBean("test");
                    student.hello();
```

那么现在又有新的问题了 IoC容器创建的Bean是只有一个还是每次索要的时候都会给我们一个新的对象? 我们现在在主方法中连续获取两次Bean对象:

```java
                    Student student1 = context.getBean(Student.class);
                    Student student2 = context.getBean(Student.class);
                    System.out.println(student1 == student2); // 默认为单例模式 对象始终为同一个
```

我们发现 最后得到的结果为true 那么说明每次从IoC容器获取到的对象 始终都是同一个 默认情况下 通过IoC容器进行管理的Bean都是单例模式的 这个对象只会被创建一次

如果我们希望每次拿到的对象都是一个新的 我们也可以将其作用域进行修改:

<img src="https://image.itbaima.net/markdown/2022/11/22/hDGo7m9uBlgVn5A.png"/>

这里一共有两种作用域 第一种是singleton 默认情况下就是这一种 当然还有prototype 表示为原型模式(为了方便叫多例模式也行) 这种模式每次得到的对象都是一个新的:

```java
                    Student student1 = context.getBean(Student.class); // 原型模式下 对象不再始终是同一个了
                    Student student2 = context.getBean(Student.class);
                    System.out.println(student1 == student2);
```

实际上 当Bean的作用域为单例模式时 那么它会在一开始(容器加载配置时)就被创建 我们之后拿到的都是这个对象 而处于原型模式下 只有在获取时才会被创建
也就是说 单例模式下 Bean会被IoC容器存储 只要容器没有被销毁 那么此对象将一直存在 而原型模式才是相当于在要用的时候直接new了一个对象 并不会被保存

当然 如果我们希望单例模式下的Bean不用再一开始就加载 而是一样等到需要时再加载(加载后依然会被容器存储 之后一直使用这个对象了 不会再创建新的) 我们也可以开启懒加载

```xml
                    <bean class="com.test.bean.Student" lazy-init="true"/>
```

开启懒加载后 只有在真正第一次使用时才会创建对象

因为单例模式下Bean是由IoC容器加载 但是加载顺序我们并不清楚 如果我们需要维护Bean的加载顺序(比如某个Bean必须要在另一个Bean之前创建
那么我们可以使用depends-on来设定前置加载Bean 这样被依赖的Bean一定会在之前加载 比如Teacher应该在Student之前加载:

```xml
                    <bean name="teacher" class="com.test.bean.Teacher"/>
                    <bean name="student" class="com.test.bean.Student" depends-on="teacher"/>
```

这样就可以保证Bean的加载顺序了

### 依赖注入
依赖注入(Dependency Injection, DI)是一种设计模式 也是Spring框架的核心概念之一 现在我们已经了解了如何注册和使用一个Bean
但是这样还远远不够 还记得我们一开始说的 消除类之间的强关联吗? 比如现在有一个教师接口:

```java
                    public interface Teacher {
                        void teach();
                    }
```

具体的实现有两个:

```java
                    public class ArtTeacher implements Teacher {
    
                        @Override
                        public void teach() {
                            System.out.println("我是美术老师 我教你画画");
                        }
                        
                    }
```
```java
                    public class ProgramTeacher implements Teacher {
    
                        @Override
                        public void teach() {
                            System.out.println("我是编程老师 我教你学Rust");
                        }
                        
                    }
```

我们的学生一开始有一个老师教他 比如美术老师:

```java
                    public class Student {
                    
                        private Teacher teacher = new ArtTeacher();   
                      	// 在以前 如果我们需要制定哪个老师教我们 直接new创建对应的对象就可以了
                        public void study(){
                            teacher.teach();
                        }
                        
                    }
```

但是我们发现 如果美术老师不教了 现在来了一个其他的老师教学生 那么就需要去修改Student类的定义:

```java
                    public class Student {
                        private Teacher teacher = new ProgramTeacher();
  	                        ...
```

可以想象一下 如果现在冒出来各种各样的类都需要这样去用Teacher 那么一旦Teacher的实现发生变化 会导致我们挨个对之前用到Teacher的类进行修改 这就很难受了

而有了依赖注入之后 Student中的Teacher成员变量 可以由IoC容器来选择一个合适的Teacher对象进行赋值 也就是说 IoC容器在创建对象时
需要将我们预先给定的属性注入到对象中 非常简单 我们可以使用property标签来实现 我们将bean标签展开:

```xml
                    <bean name="teacher" class="com.test.bean.ProgramTeacher"/>
                    <bean name="student" class="com.test.bean.Student">
                        <property name="teacher" ref="teacher"/>
                    </bean>
```

同时我们还需要修改一下Student类 依赖注入要求对应的属性必须有一个set方法:

```java
                    public class Student {
    
                        private Teacher teacher;
  	                    // 要使用依赖注入 我们必须提供一个set方法(无论成员变量的访问权限是什么) 命名规则依然是驼峰命名法
                        public void setTeacher(Teacher teacher) {
                            this.teacher = teacher;
                        }
                        ...
```

<img src="https://image.itbaima.net/markdown/2022/11/22/wu2EYC8HToJbsva.png"/>

使用property来指定需要注入的值或是一个Bean 这里我们选择ProgramTeacher 那么在使用时 Student类中的得到的就是这个Bean的对象了:

```java
                    Student student = context.getBean(Student.class);
                    student.study();
```

<img src="https://image.itbaima.net/markdown/2022/11/22/n3zYWvIoE8CrRDT.png"/>

可以看到 现在我们的Java代码中 没有出现任何的具体实现类信息(ArtTeacher, ProgramTeacher都没出现)取而代之的是那一堆xml配置 这样 就算我们切换老师的实现为另一个类 也不用去调整代码 只需要变动一下Bean的类型就可以

```xml
                    <!-- 只需要修改这里的class即可 现在改为ArtTeacher -->
                    <bean name="teacher" class="com.test.bean.ArtTeacher"/>
                    <bean name="student" class="com.test.bean.Student">
                        <property name="teacher" ref="teacher"/>
                    </bean>
```

这样 这个Bean的class就变成了新的类型 并且我们不需要再去调整其他位置的代码 再次启动程序:

<img src="https://image.itbaima.net/markdown/2022/11/22/evKArqDYcIQPCXT.png"/>

通过依赖注入 是不是开始逐渐感受到Spring为我们带来的便利了? 当然 依赖注入并不一定要注入其他的Bean 也可以是一个简单的值:

```xml
                    <bean name="student" class="com.test.bean.Student">
                        <property name="name" value="卢本伟"/>
                    </bean>
```

直接使用value可以直接传入一个具体值

实际上 在很多情况下 类中的某些参数是在构造方法中就已经完成的初始化 而不是创建之后 比如:

```java
                    public class Student {
    
                        private final Teacher teacher; // 构造方法中完成 所以说是一个final变量
                    
                        public Student(Teacher teacher){ // Teacher属性是在构造方法中完成的初始化
                            this.teacher = teacher;
                        }
  	                    ...
```

我们前面说了 Bean实际上是由IoC容器进行创建的 但是现在我们修改了默认的无参构造 可以看到配置文件里面报错了:

<img src="https://image.itbaima.net/markdown/2022/11/22/5HN8GKQywWaYvrF.png"/>

很明显 是因为我们修改了构造方法 IoC容器默认只会调用无参构造 所以 我们需要指明一个可以用的构造方法 我们展开bean标签 添加一个constructor-arg标签:

```xml
                    <bean name="teacher" class="com.test.bean.ArtTeacher"/>
                    <bean name="student" class="com.test.bean.Student">
                        <constructor-arg name="teacher" ref="teacher"/>
                    </bean>
```

这里的constructor-arg就是构造方法的一个参数 这个参数可以写很多个 会自动匹配符合里面参数数量的构造方法 这里匹配的就是我们刚刚编写的需要一个参数的构造方法

<img src="https://image.itbaima.net/markdown/2022/11/22/evKArqDYcIQPCXT.png"/>

通过这种方式 我们也能实现依赖注入 只不过现在我们将依赖注入的时机提前到了对象构造时

那要是出现这种情况呢? 现在我们的Student类中是这样定义的:

```java
                    public class Student {
    
                        private final String name;
                        
                        public Student(String name){
                            
                            System.out.println("我是一号构造方法");
                            this.name = name;
                            
                        }
                    
                        public Student(int age){
                            
                            System.out.println("我是二号构造方法");
                            this.name = String.valueOf(age);
                            
                        }
                        
                    }
```

此时我们希望使用的是二号构造方法 那么怎么才能指定呢? 有二种方式 我们可以给标签添加类型:

```xml
                    <constructor-arg value="1" type="int"/>
```

也可以指定为对应的参数名称:

```xml
                    <constructor-arg value="1" name="age"/>
```

反正只要能够保证我们指定的参数匹配到目标构造方法即可

现在我们的类中出现了一个比较特殊的类型 它是一个集合类型:

```xml
                    public class Student {

                        private List<String> list;
                    
                        public void setList(List<String> list) {
                            this.list = list;
                        }
        
                    }
```

对于这种集合类型 有着特殊的支持:

```xml
                    <bean name="student" class="com.test.bean.Student">
                      	<!-- 对于集合类型 我们可以直接使用标签编辑集合的默认值 -->
                        <property name="list">
                            <list>
                                <value>AAA</value>
                                <value>BBB</value>
                                <value>CCC</value>
                            </list>
                        </property>
                    </bean>
```

不仅仅是List, Map, Set这类常用集合类包括数组在内 都是支持这样编写的 比如Map类型 我们也可以使用entry来注入:

```xml
                    <bean name="student" class="com.test.bean.Student">
                        <property name="map">
                            <map>
                                <entry key="语文" value="100.0"/>
                                <entry key="数学" value="80.0"/>
                                <entry key="英语" value="92.5"/>
                            </map>
                        </property>
                    </bean>
```

至此 我们就已经完成了两种依赖注入的学习:
- Setter依赖注入: 通过成员属性对应的set方法完成注入
- 构造方法依赖注入: 通过构造方法完成注入

### 自动装配
在之前 如果我们需要使用依赖注入的话 我们需要对property参数进行配置:

```xml
                    <bean name="student" class="com.test.bean.Student">
                        <property name="teacher" ref="teacher"/>
                    </bean>
```

但是有些时候为了方便 我们也可以开启自动装配 自动装配就是让IoC容器自己去寻找需要填入的值 我们只需要将set方法提供好就可以了 这里需要添加autowire属性:

```xml
                    <bean name="student" class="com.test.bean.Student" autowire="byType"/>
```

autowire属性有两个值普通 一个是byName 还有一个是byType 顾名思义 一个是根据类型去寻找合适的Bean自动装配 还有一个是根据名字去找 这样我们就不需要显式指定property了

<img src="https://image.itbaima.net/markdown/2022/11/22/QIBRwScq6fu4XDm.png"/>

此时set方法旁边会出现一个自动装配图标 效果和上面是一样的

对于使用构造方法完成的依赖注入 也支持自动装配 我们只需要将autowire修改为:

```xml
                    <bean name="student" class="com.test.bean.Student" autowire="constructor"/>
```

这样 我们只需要提供一个对应参数的构造方法就可以了(这种情况默认也是byType寻找的):

<img src="https://image.itbaima.net/markdown/2022/11/22/rgl7fXJ2ZKAU8Rd.png"/>

这样同样可以完成自动注入:

<img src="https://image.itbaima.net/markdown/2022/11/22/evKArqDYcIQPCXT.png"/>

自动化的东西虽然省事 但是太过机械 有些时候 自动装配可能会遇到一些问题 比如出现了下面的情况:

<img src="https://image.itbaima.net/markdown/2022/11/22/SQTchJBq4G8NWyC.png"/>

此时 由于autowire的规则为byType 存在两个候选Bean 但是我们其实希望ProgramTeacher这个Bean在任何情况下都不参与到自动装配中 此时我们就可以将它的自动装配候选关闭:

```xml
                    <bean name="teacher" class="com.test.bean.ArtTeacher"/>
                    <bean name="teacher2" class="com.test.bean.ProgramTeacher" autowire-candidate="false"/>
                    <bean name="student" class="com.test.bean.Student" autowire="byType"/>
```

当autowire-candidate设定false时 这个Bean将不再作为自动装配的候选Bean 此时自动装配候选就只剩下一个唯一的Bean了 报错消失 程序可以正常运行

除了这种方式 我们也可以设定primary属性 表示这个Bean作为主要的Bean 当出现歧义时 也会优先选择:

```xml
                    <bean name="teacher" class="com.test.bean.ArtTeacher" primary="true"/>
                    <bean name="teacher2" class="com.test.bean.ProgramTeacher"/>
                    <bean name="student" class="com.test.bean.Student" autowire="byType"/>
```

这样写程序依然可以正常运行 并且选择的也是ArtTeacher(就是不知道为什么IDEA会上红标 BUG?)

### 生命周期与继承
除了修改构造方法 我们也可以为Bean指定初始化方法和销毁方法 以便在对象创建和被销毁时执行一些其他的任务:

```java
                    public void init(){
                        System.out.println("我是对象初始化时要做的事情");
                    }
                    
                    public void destroy(){
                        System.out.println("我是对象销毁时要做的事情");
                    }
```

我们可以通过init-method和destroy-method来指定:

```xml
                    <bean name="student" class="com.test.bean.Student" init-method="init" destroy-method="destroy"/>
```

那么什么时候是初始化 什么时候又是销毁呢?

```java
                    // 当容器创建时 默认情况下Bean都是单例的 那么都会在一开始就加载好 对象构造完成后 会执行init-method
                    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
                    // 我们可以调用close方法关闭容器 此时容器内存放的Bean也会被一起销毁 会执行destroy-method
                    context.close();
```

所以说 最后的结果为:

<img src="https://image.itbaima.net/markdown/2022/11/23/GWIyPDOaK4TAM1N.png"/>

注意 如果Bean不是单例模式 而是采用的原型模式 那么就只会在获取时才创建 并调用init-method 而对应的销毁方法不会被调用
(因此 对于原型模式下的Bean Spring无法顾及其完整生命周期 而在单例模式下 Spring能够从Bean对象的创建一直管理到对象的销毁) 官方文档原文如下:

    In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean.
    The container instantiates, configures, and otherwise assembles a prototype object and hands
    it to the client, with no further record of that prototype instance. Thus, although initialization
    lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes,
    configured destruction lifecycle callbacks are not called. The client code must clean up prototype-scoped
    objects and release expensive resources that the prototype beans hold. To get the Spring container
    to release resources held by prototype-scoped beans, try using a custom bean post-processor,
    which holds a reference to beans that need to be cleaned up.

Bean之间也是具备继承关系的 只不过这里的继承并不是类的继承 而是属性的继承 比如:

```java
                    public class SportStudent {
    
                        private String name;
                    
                        public void setName(String name) {
                            this.name = name;
                        }
                        
                    }
```
```java
                    public class ArtStudent {
                    
                        private String name;
                       
                        public void setName(String name) {
                            this.name = name;
                        }
                        
                    }
```

此时 我们先将ArtStudent注册一个Bean:

```xml
                    <bean name="artStudent" class="com.test.bean.ArtStudent">
                        <property name="name" value="小明"/>
                    </bean>
```

这里我们会注入一个name的初始值 此时我们创建了一个SportStudent的Bean 我们希望这个Bean的属性跟刚刚创建的Bean属性是一样的 那么我们可以写一个一模一样的:

```xml
                    <bean class="com.test.bean.SportStudent">
                        <property name="name" value="小明"/>
                    </bean>
```

但是如果属性太多的话 是不是写起来有点麻烦? 这种情况 我们就可以配置Bean之间的继承关系了 我们可以让SportStudent这个Bean直接继承ArtStudent这个Bean配置的属性














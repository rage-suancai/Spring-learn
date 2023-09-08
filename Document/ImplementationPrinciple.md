## 实现原理探究(选学)
注意: 本版块难度很大 所有内容都作为选学内容

如果学习Spring基本内容对你来说已经非常困难了 建议跳过此小节 直接进入MVC阶段的学习 此小节会从源码角度解释Spring的整个运行原理 对初学者来说对等同于小学跨越到高中
它并不是必学内容 但是对于个人阅厉提升极为重要(推荐完成整个SSM阶段的学习并且加以实战之后再来看此部分) 如果你还是觉得自己能够跟上节奏继续深入钻研底层原理 那么现在就开始吧

### Bean工厂与Bean定义
实际上我们之前的所有操作都离不开一个东西 那就是IoC容器 那么它到底是如何实现呢? 这一部分我们将详细介绍 首先我们大致了解一下ApplicationContext的加载流程:

<img src="https://image.itbaima.net/markdown/2022/12/17/Un6qjPci2uvkL5X.png"/>

我们可以看到 整个过程极为复杂 一句话肯定是无法解释的 由于Spring的源码非常庞大 因此我们不可能再像了解其他框架那样直接自底向上干源码了(各位可以自己点开看看 代码量非常多)

<img src="https://image.itbaima.net/markdown/2022/12/17/QXqvO1sGh6d4ZSz.png"/>

我们只能对几个关键部分进行介绍 在了解这些内容之后 实际上不需要完全阅读所有部分的源码都可以有一个大致的认识

首先 容器既然要管理Bean 那么肯定需要一个完善的管理机制 实际上 对Bean的管理都是依靠BeanFactory在进行
顾名思义BeanFactory就是对Bean进行生产和管理的工厂 我们可以尝试自己创建和使用BeanFactory对象:

```java
                    public static void main(String[] args) {
    
                        BeanFactory factory = new DefaultListableBeanFactory(); // 这是BeanFactory的一个默认实现类
                        System.out.println("获取Bean对象: " + factory.getBean("lbwnb")); // 我们可以直接找工厂获取Bean对象
        
                    }
```

我们可以直接找Bean工厂索要对象 只不过在一开始 工厂并不知道自己需要生产什么 可以生产什么 因此我们直接索要一个工厂不知道的Bean对象 会直接得到:

<img src="https://image.itbaima.net/markdown/2023/02/14/n54N3iFQX7awHAl.png"/>

我们只有告诉工厂我们要生产什么 怎么生产 工厂才能开工:

```java
                    public static void main(String[] args) {
    
                        DefaultListableBeanFactory factory = new DefaultListableBeanFactory(); // 这是BeanFactory的一个默认实现类
                    
                        BeanDefinition definition = BeanDefinitionBuilder // 使用BeanDefinitionBuilder快速创建Bean定义
                                .rootBeanDefinition(Student.class) // Bean的类型
                                .setScope("prototype") // 设置作用域为原型模式
                                .getBeanDefinition(); // 生成此Bean定义
                        factory.registerBeanDefinition("lbwnb", definition); // 向工厂注册Bean此定义 并设定Bean的名称
                    
                        System.out.println(factory.getBean("lbwnb")); // 现在就可以拿到了
        
                    }
```

实际上 我们的ApplicationContext中就维护了一个AutowireCapableBeanFactory对象:

```java
                    public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
                     	@Nullable
                    	private volatile DefaultListableBeanFactory beanFactory; // 默认构造后存放在这里的是一个DefaultListableBeanFactory对象
                      
                        ...
                        
                        @Override
                        public final ConfigurableListableBeanFactory getBeanFactory() { // getBeanFactory就可以直接得到上面的对象了
                           DefaultListableBeanFactory beanFactory = this.beanFactory;
                           if (beanFactory == null) {
                              throw new IllegalStateException("BeanFactory not initialized or already closed - " +
                                    "call 'refresh' before accessing beans via the ApplicationContext");
                           }
                           return beanFactory;
                        }
```

我们可以尝试获取一下:

```java
                    ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
                    // 我们可以直接获取此对象
                    System.out.println(context.getAutowireCapableBeanFactory());
```

正是因为这样 ApplicationContext才具有了管理和生产Bean对象的能力

不过 我们的配置可能是XML 可能是配置类 那么Spring要如何进行解析 将这些变成对应的BeanDefinition对象呢? 使用BeanDefinitionReader就可以:

```java
                    public static void main(String[] args) {
    
                        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
                        // 比如我们要读取XML配置 我们直接使用XmlBeanDefinitionReader就可以快速进行扫描
                        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
                        // 加载此XML文件中所有的Bean定义到Bean工厂中
                        reader.loadBeanDefinitions(new ClassPathResource("application.xml"));
                        
                        // 可以看到能正常生产此Bean的实例对象
                        System.out.println(factory.getBean(Student.class));
                        
                    }
```

因此 针对于不同的配置方式 ApplicationContext有着多种实现 其中常用的有:
- ClassPathXmlApplicationContext: 适用于类路径下的XML配置文件
- FileSystemXmlApplicationContext: 适用于非类路径下的XML配置文件
- AnnotationConfigApplicationContext: 适用于注解配置形式

比如ClassPathXmlApplicationContext在初始化的时候就会创建一个对应的XmlBeanDefinitionReader进行扫描:

```java
                    @Override
                    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
                       // 为给定的BeanFactory创建XmlBeanDefinitionReader便于读取XML中的Bean配置
                       XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
                    
                       // 各种配置 忽略掉
                       beanDefinitionReader.setEnvironment(this.getEnvironment());
                       ...
                       // 配置完成后 直接开始加载XML文件中的Bean定义
                       loadBeanDefinitions(beanDefinitionReader);
                    }
```
```java
                    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
                       Resource[] configResources = getConfigResources(); // 具体加载过程我就不详细介绍了
                       if (configResources != null) {
                          reader.loadBeanDefinitions(configResources);
                       }
                       String[] configLocations = getConfigLocations();
                       if (configLocations != null) {
                          reader.loadBeanDefinitions(configLocations);
                       }
                    }
```

现在 我们就已经知道 Bean实际上是一开始通过BeanDefinitionReader进行扫描 















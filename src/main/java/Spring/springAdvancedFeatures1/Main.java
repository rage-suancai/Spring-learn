package Spring.springAdvancedFeatures1;

import Spring.springAdvancedFeatures1.config.MainConfiguration;
import Spring.springAdvancedFeatures1.entity.Student1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean Aware
 * 在Spring中提供了一些以Aware结尾的接口 实现了Aware接口的bean在被初始化之后 可以获取相应资源 Aware的中文意思为感知 简单来说 它就是一个标识
 * 实现此接口的类会获得某些感知能力 Spring容器会在Bean被加载时 根据类实现的感知接口 会调用类中实现的对应感知方法
 *
 * 比如BeanNameAware之类的以Aware结尾的接口 这个接口接口的资源就是BeanName:
 *
 *                  @Component
 *                  public class Student implements BeanNameAware { // 我们只需要实现这个接口就可以了
 *
 *                      @Override
 *                      public void setBeanName(String name) { //Bean在加载的时候 容器就会自动调用此方法 将Bean的名称给到我们
 *                          System.out.println("我在加载阶段获得了Bean名字: " + name);
 *                      }
 *
 *                  }
 *
 * 又比如BeanClassLoaderAware 那么它能够使得我们可以在Bean加载阶段获取到当前Bean的类加载器:
 *
 *                  @Component
 *                  public class Student implements BeanClassLoaderAware {
 *
 *                      @Override
 *                      public void setBeanClassLoader(ClassLoader classLoader) {
 *                          System.out.println(classLoader);
 *                      }
 *
 *                  }
 *
 * 有关其他的Aware这里就不一一列举了 我们会在后面的实现原理探究部分逐步认识的
 */
public class Main {

    static void test1() {

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        Student1 student1 = context.getBean(Student1.class);
        student1.setBeanName("马化腾");

    }

    public static void main(String[] args) {

        test1();

    }

}

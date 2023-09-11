package Spring.PrincipleTest;

import Spring.PrincipleTest.entity.Student;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class Main {

    public static void main(String[] args) {

        //test1();
        //test2();
        test3();

    }

    static void test1() {

        BeanFactory factory = new DefaultListableBeanFactory();
        System.out.println("获取Bean对象: " + factory.getBean("yxsnb"));

    }

    static void test2() {

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        BeanDefinition definition = BeanDefinitionBuilder
                .rootBeanDefinition(Student.class)
                .setScope("prototype")
                .getBeanDefinition();
        factory.registerBeanDefinition("yxsnb", definition);

        System.out.println(factory.getBean("yxsnb"));

    }

    static void test3() {

        DefaultListableBeanFactory factoryParent = new DefaultListableBeanFactory();
        DefaultListableBeanFactory factoryChild = new DefaultListableBeanFactory();

        factoryParent.registerBeanDefinition("a", new RootBeanDefinition(A.class));
        factoryChild.registerBeanDefinition("b", new RootBeanDefinition(B.class));
        factoryChild.registerBeanDefinition("c", new RootBeanDefinition(C.class));
        factoryChild.setParentBeanFactory(factoryParent);

        System.out.println(factoryChild.getBean(A.class));
        System.out.println(factoryChild.getBean(B.class));
        System.out.println(factoryChild.getBean(C.class));
        System.out.println(factoryParent.getBean(B.class));

    }
    static class A { }
    static class B { }
    static class C { }

}

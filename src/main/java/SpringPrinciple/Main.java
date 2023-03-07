package SpringPrinciple;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author YXS
 * @PackageName: SpringPrinciple
 * @ClassName: Main
 * @Desription:
 * @date 2023/3/7 9:47
 */
public class Main {

    static void test1() {

        /*DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        BeanDefinition definition = BeanDefinitionBuilder
                .rootBeanDefinition(Student.class)
                .setScope("prototype")
                .getBeanDefinition();
        factory.registerBeanDefinition("lbwnb", definition);

        System.out.println(factory.getBean("lbwnb"));*/

    }

    static void test2() {

        /*DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("TestApplication.xml"));

        System.out.println(factory.getBean(Student.class));*/

    }

    static void test3() {

        /*DefaultListableBeanFactory factoryParent = new DefaultListableBeanFactory();
        DefaultListableBeanFactory factoryChild = new DefaultListableBeanFactory();

        factoryParent.registerBeanDefinition("a", new RootBeanDefinition(A.class));
        factoryChild.registerBeanDefinition("b", new RootBeanDefinition(B.class));
        factoryChild.registerBeanDefinition("c", new RootBeanDefinition(C.class));
        factoryChild.setParentBeanFactory(factoryParent);

        System.out.println(factoryChild.getBean(A.class));
        System.out.println(factoryChild.getBean(B.class));
        System.out.println(factoryChild.getBean(C.class));
        System.out.println(factoryParent.getBean(B.class));*/

    }

    static void test4() {

        //BeanFactory

    }

    public static void main(String[] args) {

        //test1();
        //test2();
        //test3();
        test4();

    }

    static class A { }
    static class B { }
    static class C { }

}

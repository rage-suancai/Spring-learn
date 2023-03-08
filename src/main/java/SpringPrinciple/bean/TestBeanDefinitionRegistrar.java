package SpringPrinciple.bean;

import SpringPrinciple.entity.Student;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author YXS
 * @PackageName: SpringPrinciple
 * @ClassName: TestBeanDefinitionRegistrar
 * @Desription:
 * @date 2023/3/8 15:31
 */
public class TestBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar  {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        BeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(Student.class).getBeanDefinition();
        registry.registerBeanDefinition("lbwnb", definition);

    }

}

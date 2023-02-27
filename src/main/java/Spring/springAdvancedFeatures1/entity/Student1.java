package Spring.springAdvancedFeatures1.entity;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @author YXS
 * @PackageName: Spring.springAdvancedFeatures1.entity
 * @ClassName: Student
 * @Desription:
 * @date 2023/2/27 9:29
 */
@Component
public class Student1 implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("我在加载阶段获得了Bean名字: " + name);
    }

}

package Spring.AdvancedFeatures.Aware;

import org.springframework.beans.factory.BeanClassLoaderAware;

public class AwareTest implements BeanClassLoaderAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println(classLoader);
    }

}

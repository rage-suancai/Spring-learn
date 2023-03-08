package SpringPrinciple.bean;

import jakarta.annotation.PostConstruct;

/**
 * @author YXS
 * @PackageName: SpringPrinciple
 * @ClassName: TestBean
 * @Desription:
 * @date 2023/3/8 15:31
 */
public class TestBean {

    @PostConstruct
    void init() {
        System.out.println("我被初始化了");
    }

}

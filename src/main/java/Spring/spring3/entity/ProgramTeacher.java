package Spring.spring3.entity;

/**
 * @author YXS
 * @PackageName: Spring.spring3.entity
 * @ClassName: ProgramTeacher
 * @Desription:
 * @date 2023/2/24 9:12
 */
public class ProgramTeacher implements Teacher {

    @Override
    public void teach() {

        System.out.println("我是编程老师 我教你学Rust");

    }

}

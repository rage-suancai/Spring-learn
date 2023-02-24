package Spring.spring3.entity;

/**
 * @author YXS
 * @PackageName: Spring.spring3.entity
 * @ClassName: ArtTeacher
 * @Desription:
 * @date 2023/2/24 9:10
 */
public class ArtTeacher implements Teacher {

    @Override
    public void teach() {

        System.out.println("我是美术老师 我教你画蒙娜丽莎");

    }

}

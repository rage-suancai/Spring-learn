package Spring.bean2;

public class ArtTeacher implements Teacher {

    @Override
    public void teach() {
        System.out.println("我是美术老师 我教你画画 ");
    }

}

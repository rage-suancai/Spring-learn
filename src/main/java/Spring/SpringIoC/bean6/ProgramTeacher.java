package Spring.SpringIoC.bean6;

public class ProgramTeacher implements Teacher {

    @Override
    public void teach() {
        System.out.println("我是编程老师 我教你学Rust");
    }

}

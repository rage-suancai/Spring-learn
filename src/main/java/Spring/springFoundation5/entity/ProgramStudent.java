package Spring.springFoundation5.entity;

import lombok.ToString;

/**
 * @author YXS
 * @PackageName: Spring.springFoundation5.entity
 * @ClassName: ProgramStudent
 * @Desription:
 * @date 2023/2/25 11:26
 */
@ToString
public class ProgramStudent {

    int id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

}

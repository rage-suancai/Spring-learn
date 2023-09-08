package Spring.Mybatis.mapper;

import Spring.Mybatis.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Insert("insert into student(name,sex) values('测试,'男')")
    void insertStudent();

}

package Spring.Mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    @Insert("insert into student(name,sex) values('测试,'男')")
    void insertStudent();

}

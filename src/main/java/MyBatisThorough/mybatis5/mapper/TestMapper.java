package MyBatisThorough.mybatis5.mapper;

import org.apache.ibatis.annotations.Insert;

public interface TestMapper {

    @Insert("insert into student(name, sex) values('崔佛', '男')")
    void  insertStudent();

}

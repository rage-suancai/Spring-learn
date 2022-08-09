package MyBatisThorough.mybatis3.mapper;

import MyBatisThorough.mybatis3.bean.Student;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {

    @Select("select * from student where sid = '1'")
    Student getStudent();

}

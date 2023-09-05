package Spring.Mybatis.mapper;

import Spring.Mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where uid = 1")
    User getUser();

}

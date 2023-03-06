package DatabaseFrameworkIntegration.IntegrationMybatis.mapper;

import DatabaseFrameworkIntegration.IntegrationMybatis.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author YXS
 * @PackageName: DatabaseFrameworkIntegration.IntegrationMybatis.Mapper
 * @ClassName: TestMapper
 * @Desription:
 * @date 2023/3/6 9:45
 */
//@Mapper
public interface TestMapper {

    @Select("select deptno,departname,location from dept_ye where deptno = '40'")
    Student getDept();

}

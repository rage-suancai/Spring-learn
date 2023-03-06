package DatabaseFrameworkIntegration.hikariCPPool.mapper;

import DatabaseFrameworkIntegration.integrationMybatis.entity.Student;
import org.apache.ibatis.annotations.Select;

/**
 * @author YXS
 * @PackageName: DatabaseFrameworkIntegration.hikariCPPool.mapper
 * @ClassName: TestMapper
 * @Desription:
 * @date 2023/3/6 9:45
 */
public interface TestMapper {

    @Select("select deptno,departname,location from dept_ye where deptno = '40'")
    Student getDept();

}

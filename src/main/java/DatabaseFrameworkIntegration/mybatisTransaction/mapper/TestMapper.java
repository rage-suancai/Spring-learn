package DatabaseFrameworkIntegration.mybatisTransaction.mapper;

import org.apache.ibatis.annotations.Insert;

/**
 * @author YXS
 * @PackageName: DatabaseFrameworkIntegration.mybatisAffairs.mapper
 * @ClassName: TestMapper
 * @Desription:
 * @date 2023/3/6 9:45
 */
public interface TestMapper {

    @Insert("insert into dept_ye(deptno,departname,location) values('deptno', 'departname', 'location')")
    void insertDept(String deptno, String departname, String location);

}

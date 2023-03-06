package DatabaseFrameworkIntegration.IntegrationMybatis.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author YXS
 * @PackageName: DatabaseFrameworkIntegration.IntegrationMybatis.config
 * @ClassName: MainConfiguration
 * @Desription:
 * @date 2023/3/6 9:17
 */
@Configuration
@MapperScan("DatabaseFrameworkIntegration.IntegrationMybatis.mapper")
public class MainConfiguration {

    @Bean
    public DataSource dataSource() {

        return new PooledDataSource("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai",
                "root", "123456");

    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;

    }

    /*@Bean
    public SqlSessionTemplate sqlSessionTemplate() throws IOException {

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
        return new SqlSessionTemplate(factory);

    }*/

}

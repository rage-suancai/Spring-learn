package MyBatisThorough.mybatis3.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan("MyBatisThorough.mybatis3.mapper")
@ComponentScan("MyBatisThorough.mybatis3.service")
public class MainConfiguration {

    /*@Bean
    public SqlSessionTemplate sqlSessionTemplate() throws IOException {
        return new SqlSessionTemplate(
                new SqlSessionFactoryBuilder()
                        .build(Resources.getResourceAsReader("mybatis-config.xml")));
    }*/

    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource source){
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(source);
        return bean;
    }

}

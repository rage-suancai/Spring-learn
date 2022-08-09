package Spring.spring7.config;

import Spring.spring7.Bean.Teacher;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainConfiguration2 {

    /*@Bean
    public Teacher teacher(){
        return new Teacher();
    }*/

    @Bean
    public Connection getConnection(){
        System.out.println("创建新的连接");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/study", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

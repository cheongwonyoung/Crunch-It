package com.kb.crunchit.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

@Configuration

public class AppConfig {
    @Bean  //(destroyMethod="close")
    public DataSource dataSource() {
        Properties properties = new Properties();
        HikariDataSource dataSource= new HikariDataSource();
        try{
            Reader reader = Resources.getResourceAsReader("config/db.properties");
            properties.load(reader);
            dataSource.setDriverClassName(properties.getProperty("db.driverClass"));
            dataSource.setJdbcUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.username"));
            dataSource.setPassword(properties.getProperty("db.password"));
            dataSource.setAutoCommit(true);

        }catch(IOException e){
            e.printStackTrace();
        }
        return dataSource;
    }
}

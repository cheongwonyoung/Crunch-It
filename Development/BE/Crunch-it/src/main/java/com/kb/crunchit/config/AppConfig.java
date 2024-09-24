package com.kb.crunchit.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            Reader reader = Resources.getResourceAsReader("properties/db.properties");
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

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setConfigLocation(
                applicationContext.getResource("classpath:/mybatis-config.xml"));
        factoryBean.setDataSource(dataSource());
        return (SqlSessionFactory) factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
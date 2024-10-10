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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

@EnableScheduling
@Configuration
@ComponentScan(basePackages = {"com.kb.crunchit.util", "com.kb.crunchit.service"})
@MapperScan(basePackages = {"com.kb.crunchit.mapper"})
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

    // WebClient.Builder 빈 추가
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    // S3Client 설정 추가
    @Bean
    public S3Client s3Client() {
        Properties properties = new Properties();
        try {
            // s3.properties 파일 로드
            Reader reader = Resources.getResourceAsReader("properties/s3.properties");
            properties.load(reader);

            // AWS 자격 증명 및 리전 설정
            String accessKey = properties.getProperty("aws_access_key_id");
            String secretKey = properties.getProperty("aws_secret_access_key");
            String region = properties.getProperty("aws_region");

            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

            return S3Client.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .region(Region.of(region))
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load S3 properties", e);
        }
    }

    // multipart config
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760);  // 10MB
        multipartResolver.setMaxUploadSizePerFile(10485760);  // 10MB
        return multipartResolver;
    }
}
package com.example.demo.config;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.SubLibrary;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;
import com.example.demo.service.SubLibraryService;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.util.Date;
import java.util.Properties;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Configuration
@EnableTransactionManagement
@EnableWebMvc
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Book book() {
        return new Book();
    }

    @Bean
    @Scope("prototype")
    public Author author(){
        return new Author();
    }

    @Bean
    @Scope("prototype")
    public SubLibrary subLibrary(){
        return new SubLibrary();
    }

//    @Bean
//    public AuthorService authorService(){
//        return new AuthorService();
//    }
//
//    @Bean
//    public BookService bookService(){
//        return new BookService();
//    }
//
//    @Bean
//    public SubLibraryService subLibraryService(){
//        return new SubLibraryService();
//    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        Properties properties = new Properties();
        //For Postgresql
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //For mysql
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setPackagesToScan("com.example.demo.model");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/final1");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Tuikhongbiet123");

        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }

}

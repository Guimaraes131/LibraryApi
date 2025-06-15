package io.github.Guimaraes131.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig hkc = new HikariConfig();

        hkc.setJdbcUrl(url);
        hkc.setUsername(username);
        hkc.setPassword(password);
        hkc.setDriverClassName(driver);

        hkc.setMaximumPoolSize(10); // max of connections released
        hkc.setMinimumIdle(1); // initial pool size
        hkc.setPoolName("library-db-pool");
        hkc.setMaxLifetime(600000); // 5 minutes
        hkc.setConnectionTimeout(100000);
        hkc.setConnectionTestQuery("select 1");

        return new HikariDataSource(hkc);
    }
}

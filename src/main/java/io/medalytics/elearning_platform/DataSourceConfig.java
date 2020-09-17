package io.medalytics.elearning_platform;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.url}") String url;
    @Value("${spring.datasource.driver-class-name}") String jdbcDriver;
    @Value("${spring.datasource.hikari.maximum-pool-size}") int poolSize;
    @Value("${spring.datasource.data-username}") String username;
    @Value("${spring.datasource.data-password}") String password;

    @Bean
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setDriverClassName(jdbcDriver);
        config.setMaximumPoolSize(poolSize);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }


}

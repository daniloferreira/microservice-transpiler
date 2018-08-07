package com.example.bpmpoc;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.activiti")
    public DataSource activitiDataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:h2:mem:activiti")
                .username("activiti")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        return baseSpringProcessEngineConfiguration(
                activitiDataSource(),
                transactionManager,
                springAsyncExecutor);
    }
}
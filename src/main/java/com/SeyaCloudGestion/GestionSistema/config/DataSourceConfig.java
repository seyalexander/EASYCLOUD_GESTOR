package com.SeyaCloudGestion.GestionSistema.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    /*@Bean(name = "MYSQL")
    @Primary
    public DataSource mysqlDataSource() {

        try {
            DriverManagerDataSource bds= new DriverManagerDataSource();
            bds.setDriverClassName(
                    environment.getRequiredProperty("spring.datasource.driver-class-name")
            );
            bds.setUrl(environment.getRequiredProperty("spring.datasource.url"));
            bds.setUsername(environment.getRequiredProperty("spring.datasource.username"));
            bds.setPassword(environment.getRequiredProperty("spring.datasource.password"));
            return bds;

        } catch (Exception e) {
            log.error("ERROR al crear DataSource MySQL: {}", e.getMessage());
            log.error("Stack trace: ", e);
            throw new RuntimeException("No se pudo crear el DataSource MySQL", e);
        }
    }*/

    @Bean(name = "SQLSERVER")
    public DataSource sqlServerDataSource() {

        try {
            DriverManagerDataSource bdss= new DriverManagerDataSource();
            bdss.setDriverClassName(
                    environment.getRequiredProperty("spring.datasource.SQL.driver-class-name")
            );

            bdss.setUrl(environment.getRequiredProperty("spring.datasource.SQL.url"));
            bdss.setUsername(environment.getRequiredProperty("spring.datasource.SQL.username"));
            bdss.setPassword(environment.getRequiredProperty("spring.datasource.SQL.password"));
            return bdss;
        } catch (Exception e) {
            log.error("ERROR al crear DataSource SQL Server: {}", e.getMessage());
            throw new RuntimeException("No se pudo crear el DataSource SQL Server", e);
        }
    }

    /*@Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("MYSQL") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }*/

    @Bean(name = "sqlServerTransactionManager")
    public PlatformTransactionManager sqlServerTransactionManager(
            @Qualifier("SQLSERVER") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}

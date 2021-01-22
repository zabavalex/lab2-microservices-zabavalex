package com.rsoilab2.store.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayConfig {

    @Bean
    @Autowired
    @DependsOn("entityManagerFactory")
    public Flyway getFlyway(DataSource dataSource, FlywayProperties flywayProperties){
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayProperties.getLocations().toArray(new String[0]))
                .outOfOrder(flywayProperties.isOutOfOrder())
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .schemas(flywayProperties.getSchemas().toArray(new String[0]))
                .load();
    }

    @Bean
    @Autowired
    public FlywayMigrationInitializer flywayMigrationInitializer(DataSource dataSource, FlywayProperties flywayProperties){
        return new FlywayMigrationInitializer(getFlyway(dataSource, flywayProperties));
    }
}

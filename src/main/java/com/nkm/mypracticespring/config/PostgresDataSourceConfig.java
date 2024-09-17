package com.nkm.mypracticespring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManagerFactoryBean",
        basePackages = "com.nkm.mypracticespring.test.postgres"
)
public class PostgresDataSourceConfig {

    @Bean
    public DataSource postgresDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/test_database");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("admin");

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("postgres::springHikariCP");
        hikariConfig.setConnectionTimeout(100000);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public EntityManagerFactoryBuilder postgresEntityManagerFactoryBuilder() {
        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);

        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.ddl-auto", "update");
        jpaProperties.put("hibernate.format_sql", "true");

        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties, null);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactoryBean(
            @Qualifier("postgresDataSource") DataSource postgresDataSource) {

        return postgresEntityManagerFactoryBuilder()
                .dataSource(postgresDataSource)
                .packages("com.nkm.mypracticespring.test.postgres")
                .build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactoryBean) {
        return new JpaTransactionManager(postgresEntityManagerFactoryBean.getObject());
    }

}

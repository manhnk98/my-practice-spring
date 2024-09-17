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
        entityManagerFactoryRef = "mysqlEntityManagerFactoryBean",
        basePackages = "com.nkm.mypracticespring.test.mysql"
)
public class MysqlDataSourceConfig {

    @Bean
    public DataSource mysqlDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test_database");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("admin");

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setPoolName("mysql::springHikariCP");
        hikariConfig.setConnectionTimeout(100000);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder() {
        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);

        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.ddl-auto", "update");
        jpaProperties.put("hibernate.format_sql", "true");

        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties, null);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryBean(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource) {

        return mysqlEntityManagerFactoryBuilder()
                .dataSource(mysqlDataSource)
                .packages("com.nkm.mypracticespring.test.mysql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("mysqlEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactoryBean) {
        return new JpaTransactionManager(mysqlEntityManagerFactoryBean.getObject());
    }

}

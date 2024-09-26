//package com.nkm.mypracticespring.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "postgresEntityManagerFactoryBean",
//        basePackages = "com.nkm.mypracticespring.test.postgres",
//        transactionManagerRef = "postgresTransactionManager"
//)
//public class PostgresDataSourceConfig {
//
//    @Bean
//    public DataSource postgresDataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName("org.postgresql.Driver");
//        hikariConfig.setJdbcUrl(EnvConfig.DB_POSTRGESQL_URL);
//        hikariConfig.setUsername(EnvConfig.DB_POSTRGESQL_USERNAME);
//        hikariConfig.setPassword(EnvConfig.DB_POSTRGESQL_PASSWORD);
//
//        hikariConfig.setConnectionTestQuery("SELECT 1");
//        hikariConfig.setPoolName("Postgres::springHikariCP");
//        hikariConfig.setConnectionTimeout(EnvConfig.DB_POSTRGESQL_CONNECTION_TIMEOUT);
//        hikariConfig.setMaximumPoolSize(EnvConfig.DB_POSTRGESQL_MAX_POOL_SIZE);
//        hikariConfig.setMinimumIdle(EnvConfig.DB_POSTRGESQL_MIN_IDLE);
//
//        return new HikariDataSource(hikariConfig);
//    }
//
//    @Bean
//    public EntityManagerFactoryBuilder postgresEntityManagerFactoryBuilder() {
//        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setShowSql(false);
//        vendorAdapter.setGenerateDdl(true);
//
//        Map<String, String> jpaProperties = new HashMap<>();
//        jpaProperties.put("hibernate.ddl-auto", "update");
//        jpaProperties.put("hibernate.format_sql", "true");
//
//        return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties, null);
//    }
//
//    @Bean
////    @Primary
//    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactoryBean(
//            @Qualifier("postgresDataSource") DataSource postgresDataSource) {
//
//        return postgresEntityManagerFactoryBuilder()
//                .dataSource(postgresDataSource)
//                .packages("com.nkm.mypracticespring.test.postgres")
//                .build();
//    }
//
//    @Bean(name = "postgresTransactionManager")
////    @Primary
//    public PlatformTransactionManager postgresTransactionManager(
//            @Qualifier("postgresEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactoryBean) {
//        return new JpaTransactionManager(postgresEntityManagerFactoryBean.getObject());
//    }
//
//}

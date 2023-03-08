//package com.codestates.config;
//
//import com.atomikos.jdbc.AtomikosDataSourceBean;
//import com.mysql.cj.jdbc.MysqlXADataSource;
//import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@EnableJpaRepositories( // JpaRepository가 위치한 패키지 경로를 바꿔줌.
//        basePackages = {"com.codestates.backup"},
//        entityManagerFactoryRef = "backupEntityManager"
//)
//@Configuration
//public class XaBackupConfig {
//    @Bean
//    public DataSource dataSourceBackup() {
//        // MySQL 접속 정보 (XaCoffeeOrderConfig와 다름)
//        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
//        mysqlXADataSource.setURL("jdbc:mysql://localhost:3306/backup_data" +
//                "?allowPublicKeyRetrieval=true" +
//                "&characterEncoding=UTF-8");
//        mysqlXADataSource.setUser("backup");
//        mysqlXADataSource.setPassword("backup");
//
//        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
//        atomikosDataSourceBean.setUniqueResourceName("xaMySQLBackupMember");
//
//        return atomikosDataSourceBean;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean backupEntityManager() {
//        LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase(Database.MYSQL);
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.format_sql", "true");
//        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
//        properties.put("javax.persistence.transactionType", "JTA");
//
//        emFactoryBean.setDataSource(dataSourceBackup());
//
//        // EntityManager가 사용할 Entity 클래스(여기서는 BackupMember)가 위치한 패키지 경로를 변경
//        emFactoryBean.setPackagesToScan(new String[]{"com.codestates.backup"});
//        emFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        emFactoryBean.setPersistenceUnitName("backupPersistenceUnit");
//        emFactoryBean.setJpaPropertyMap(properties);
//
//        return emFactoryBean;
//    }
//}

//여긴 분산 트랜잭션파트 (mysql 이용)
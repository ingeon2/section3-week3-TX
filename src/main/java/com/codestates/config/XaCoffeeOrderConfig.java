//package com.codestates.config;
//
//import com.mysql.cj.jdbc.MysqlXADataSource;
//import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
//import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//// JpaRepository 활성화, 데이터베이스를 사용하기 위한 JpaReposiroty가 위치한 패키지와 entityManagerFactory 빈에 대한 참조를 설정
//@EnableJpaRepositories(
//        basePackages = {"com.codestates.member", //기존에 사용하던 JpaRepository를 그대로 사용하도록 해당 Repository가 있는 패키지 경로
//                "com.codestates.stamp",
//                "com.codestates.order",
//                "com.codestates.coffee"},
//        entityManagerFactoryRef = "coffeeOrderEntityManager" //(3)의 Bean 생성 메서드 명
//)
//@Configuration
//public class XaCoffeeOrderConfig { // 커피 주문을 위한 데이터베이스(XaCoffeeOrderConfig) 설정
//    // 데이터소스 생성, 데이터베이스에 대한 데이터소스를 생성하기 위해 데이터베이스 접속 정보들을 설정
//    @Primary
//    @Bean
//    public DataSource dataSourceCoffeeOrder() {
//        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
//        mysqlXADataSource.setURL("jdbc:mysql://localhost:3306/coffee_order" +
//                "?allowPublicKeyRetrieval=true" +
//                "&characterEncoding=UTF-8");
//        mysqlXADataSource.setUser("guest");
//        mysqlXADataSource.setPassword("guest");
//
//        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
//        atomikosDataSourceBean.setUniqueResourceName("xaCoffeeOrder");
//
//        return atomikosDataSourceBean;
//    }
//
//    // (3) EntityManagerFactoryBean 설정, JPA의 EntityManager를 얻기 위해서 LocalContainerEntityManagerFactoryBean을 사용
//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean coffeeOrderEntityManager() {
//        //LocalContainerEntityManagerFactoryBean에서 사용하는 어댑터 중에서 우리가 사용하는 HibernateJpaVendorAdapter를 설정해주고,
//        //Hibernate에서 필요한 설정 정보를 Map으로 설정
//        LocalContainerEntityManagerFactoryBean emFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase(Database.MYSQL);
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.format_sql", "true");
//
//        // JTA Platform의 이름을 추가해주어야 함!
//        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
//        properties.put("javax.persistence.transactionType", "JTA");
//
//        emFactoryBean.setDataSource(dataSourceCoffeeOrder());
//        emFactoryBean.setPackagesToScan(new String[]{
//                "com.codestates.member",
//                "com.codestates.stamp",
//                "com.codestates.order",
//                "com.codestates.coffee"
//        });
//        emFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        emFactoryBean.setPersistenceUnitName("coffeeOrderPersistenceUnit");
//        emFactoryBean.setJpaPropertyMap(properties);
//
//        return emFactoryBean;
//    }
//}

//여긴 분산 트랜잭션파트 (mysql 이용)
//package com.codestates.config;
//
//import javax.transaction.TransactionManager;
//import javax.transaction.UserTransaction;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//import com.atomikos.icatch.jta.UserTransactionImp;
//import com.atomikos.icatch.jta.UserTransactionManager;
//
//@Configuration
//public class JtaConfig { // JTA TransactionManager 설정
//    // UserTransaction은 애플리케이션이 트랜잭션 경계에서 관리되는 것을 명시적으로 정의
//    @Bean(name = "userTransaction")
//    public UserTransaction userTransaction() throws Throwable {
//        UserTransactionImp userTransactionImp = new UserTransactionImp();
//        userTransactionImp.setTransactionTimeout(10000);
//        return userTransactionImp;
//    }
//
//    @Bean(name = "atomikosTransactionManager")
//    public TransactionManager atomikosTransactionManager() throws Throwable {
//        // UserTransaction을 관리하는 UserTransactionManager를 생성한 후에 (3)과 같이 AtomikosJtaPlatform의 트랜잭션 매니저로 설정
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setForceShutdown(false);
//
//        // (3)
//        AtomikosJtaPlatform.transactionManager = userTransactionManager;
//
//        return userTransactionManager;
//    }
//
//    @Bean(name = "transactionManager")
//    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
//    public PlatformTransactionManager transactionManager() throws Throwable {
//        UserTransaction userTransaction = userTransaction();
//
//        AtomikosJtaPlatform.transaction = userTransaction;
//
//        TransactionManager atomikosTransactionManager = atomikosTransactionManager();
//
//        // (4)
//        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
//    }
//}

//여긴 분산 트랜잭션파트 (mysql 이용)
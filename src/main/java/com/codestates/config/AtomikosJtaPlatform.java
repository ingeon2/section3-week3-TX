//package com.codestates.config;
//
//import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
//
//
//import javax.transaction.TransactionManager;
//import javax.transaction.UserTransaction;
//
//
//// AbstractJtaPlatform을 상속한 후에 트랜잭션 매니저의 위치와 UserTransaction의 위치를 지정만 해주면 되는데,
//// 이 작업은 JTA TransactionManager(JtaConfig) 설정에서 이루어 짐.
//public class AtomikosJtaPlatform  extends AbstractJtaPlatform {
//    static TransactionManager transactionManager;
//    static UserTransaction transaction;
//
//    @Override
//    protected TransactionManager locateTransactionManager() {
//        return transactionManager;
//    }
//
//    @Override
//    protected UserTransaction locateUserTransaction() {
//        return transaction;
//    }
//}

//여긴 분산 트랜잭션파트 (mysql 이용)
package com.codestates.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
//import org.springframework.transaction.interceptor.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TxConfig { //애너테이션 없이 AOP로 트랜잭션 구현하려는것 in MemberService 클래스의 createMember 로직.

    //애플리케이션에 트랜잭션을 적용하기 위해서는 TransactionManager 객체가 필요, DI
    private final TransactionManager transactionManager;
    public TxConfig(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Bean //트랜잭션 어드바이스용 TransactionInterceptor 빈 등록
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();

        // 조회 메서드를 제외한 공통 트랜잭션 애트리뷰트
        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
        txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 조회 메서드에 적용하기 위한 트랜잭션 애트리뷰트 (리드온리 트루)
        RuleBasedTransactionAttribute txFindAttribute = new RuleBasedTransactionAttribute();
        txFindAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txFindAttribute.setReadOnly(true);

        // 설정한 트랜잭션 애트리뷰트는 아래와 같이 Map에 추가
        Map<String, TransactionAttribute> txMethods = new HashMap<>();
        txMethods.put("find*", txFindAttribute); //조회적용은 find*
        txMethods.put("*", txAttribute); //조회제외는 *

        // 트랜잭션 애트리뷰트를 추가한 Map 객체를 txAttributeSource.setNameMap(txMethods)으로 넘겨
        txAttributeSource.setNameMap(txMethods);

        // TransactionInterceptor 의 생성자 파라미터로 transactionManager와 txAttributeSource를 전달
        return new TransactionInterceptor(transactionManager, txAttributeSource);

    }

    @Bean
    public Advisor txAdvisor() {
        // AspectJExpressionPointcut 객체를 생성한 후, 포인트 컷 표현식으로 CoffeeService 클래스를 타겟 클래스로 지정
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.codestates.coffee.service." + "CoffeeService.*(..))");

        return new DefaultPointcutAdvisor(pointcut, txAdvice());
        // DefaultPointcutAdvisor의 생성자 파라미터로 포인트컷과 어드바이스를 전달
    }
}
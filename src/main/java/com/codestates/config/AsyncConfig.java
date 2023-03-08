package com.codestates.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {
}

//@Async 애너테이션을 사용하기 위해 @EnableAsync 애너테이션을 사용해서(필수) 만든 클래스이다
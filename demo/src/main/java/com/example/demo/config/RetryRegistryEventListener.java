// package com.example.demo.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import io.github.resilience4j.retry.RetryRegistry;
// import jakarta.annotation.PostConstruct;
// import lombok.extern.slf4j.Slf4j;

// @Component
// @Slf4j
// public class RetryRegistryEventListener {

//     @Autowired
//     private RetryRegistry registry;


//     @PostConstruct
//     public void postConstruct() {
//         //registry.retry(<resilience retry instance name>)
//         registry.retry("sessaoRetry").getEventPublisher()
//                 .onRetry(ev -> log.info("#### RetryRegistryEventListener message: {}", ev));
//     }
// }
package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.DatetimeSingleton;
import java.time.LocalDateTime;
import org.springframework.data.redis.core.RedisTemplate;

import io.github.resilience4j.retry.annotation.Retry;


@RestController
public class TestRedisPoolRetryDiController {


     public TestRedisPoolRetryDiController(RedisTemplate<String, Object> redisTemplate, DatetimeSingleton datetimeSingleton) {
        this.redisTemplate = redisTemplate;
    }

    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/retrydi")
    @Retry(name = "sessaoRetry", fallbackMethod = "fallbackSalvarDeletar")
    public String getPoolDiRetry() {
       

        redisTemplate.opsForValue().set("mykey", LocalDateTime.now().toString());

        var result = redisTemplate.opsForValue().get("mykey");

        System.out.println("result: " + result);


        //throw new RuntimeException("erro ao salvar/deletar no redis de revogados");
        //return "ok retry di";
        throw new RuntimeException("Simulando uma falha");

        
    }

    public String fallbackSalvarDeletar(RuntimeException e) {
        System.out.println("erro ao salvar/deletar no redis de revogados:" + e);
        throw new RuntimeException("erro ao salvar/deletar no redis de revogados");
        //return "";
    }
}
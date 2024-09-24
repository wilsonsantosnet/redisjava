package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.DatetimeSingleton; // Ensure this import is correct and the class exists in the specified package

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;


@RestController
public class TestRedisPoolDIController {

    private RedisTemplate<String, Object> redisTemplate;
    private DatetimeSingleton datetimeSingleton;
    

    
    public TestRedisPoolDIController(RedisTemplate<String, Object> redisTemplate, DatetimeSingleton datetimeSingleton) {
        this.redisTemplate = redisTemplate;
        this.datetimeSingleton = datetimeSingleton;
    }

    @GetMapping("/pooldi")
    public String getPoolDi() {
       



        redisTemplate.opsForValue().set("mykey", LocalDateTime.now().toString());

        var result = redisTemplate.opsForValue().get("mykey");

        System.out.println("result: " + result);

        System.out.println("datetimeSingleton: " + datetimeSingleton.getDatetime());



        return "ok pool di";

        
    }

}
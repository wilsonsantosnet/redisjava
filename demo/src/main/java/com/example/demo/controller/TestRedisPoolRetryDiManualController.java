package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.DatetimeSingleton;
import java.time.LocalDateTime;
import org.springframework.data.redis.core.RedisTemplate;



@RestController
public class TestRedisPoolRetryDiManualController {

    private int count;

     public TestRedisPoolRetryDiManualController(RedisTemplate<String, Object> redisTemplate, DatetimeSingleton datetimeSingleton) {
        this.redisTemplate = redisTemplate;
    }

    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/retrydim")
    public String getPoolDiRetry() {
      
       
        try {

            redisTemplate.opsForValue().set("mykey", LocalDateTime.now().toString());

            var result = redisTemplate.opsForValue().get("mykey");
    
            System.out.println("result: " + result);

            //throw new RuntimeException("Simular falha");
    
        } catch (Exception e) {
            
            if (count < 3) {
                count++;
                
                try {
                    Thread.sleep(10000); // 1 segundo
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }

                getPoolDiRetry();
            } else {
                throw new RuntimeException("tentei três vezes e não consegui");
            }


        }

        return "ok retry di manual";
        
    }

   
}
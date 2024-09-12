package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.github.resilience4j.retry.annotation.Retry;

import java.time.Duration;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@RestController
public class TestRedisPoolRetryController {

    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/retry")
    //@Retry(name = "sessaoRetry", fallbackMethod = "fallbackSalvarDeletar")
    public String getAccount() {
       


        String cacheHostname = "...";
        String cachekey = "...";
        Integer port = 6380;
        Integer connectTimeout = 500;
        Integer readTimeout = 500;



         //Configurando o pool de conexões
         JedisPoolConfig poolConfig = new JedisPoolConfig();
         poolConfig.setMaxTotal(150); // Número máximo de conexões
         poolConfig.setMaxIdle(20);  // Número máximo de conexões ociosas
         poolConfig.setMinIdle(5);   // Número mínimo de conexões ociosas
         poolConfig.setTestOnBorrow(true); // Testar a conexão ao pegar do pool
         poolConfig.setTestOnReturn(false); // Testar a conexão ao retornar ao pool
         
                 
        
         // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        var clientConfig = JedisClientConfiguration.builder()
            .readTimeout(Duration.ofMillis(readTimeout))
            .connectTimeout(Duration.ofMillis(connectTimeout))
            .usePooling()
            .poolConfig(poolConfig);


        clientConfig.and().useSsl();

        //RedisStandaloneConfiguration rediConfiguration = new RedisStandaloneConfiguration(cacheHostname, port);
        //rediConfiguration.setPassword(cachekey);


        RedisClusterConfiguration rediConfiguration = new RedisClusterConfiguration();
        rediConfiguration.addClusterNode(new RedisNode(cacheHostname, port));
        rediConfiguration.setPassword(cachekey);



        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(rediConfiguration, clientConfig.build());
        jedisConnectionFactory.afterPropertiesSet();
   


        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();    
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
       


        var count=0;
        while (count <=1000) {

            try {


                redisTemplate.opsForValue().set("mykey", "teste");

                var result = redisTemplate.opsForValue().get("mykey");

                System.err.println("result: " + result);


                Thread.sleep(1000);

            } catch (Exception e) {
                System.out.println(e.toString());


            }
            count++;
            
        }

        return "ok";

        
    }

    public void fallbackSalvarDeletar(Exception ex) {
        System.out.println("erro ao salvar/deletar no redis de revogados:" + ex);
        return;
    }
}
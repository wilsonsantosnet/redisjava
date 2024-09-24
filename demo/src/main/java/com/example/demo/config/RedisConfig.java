package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {


    private String _cacheHostname = "...";
    private String _cachekey = "...";
    private Integer _port = 6380;
    private Integer _connectTimeout = 150;
    private Integer _readTimeout = 150;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        //Configurando o pool de conexões
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(150); // Número máximo de conexões
        poolConfig.setMaxIdle(20);  // Número máximo de conexões ociosas
        poolConfig.setMinIdle(5);   // Número mínimo de conexões ociosas
        poolConfig.setTestOnBorrow(true); // Testar a conexão ao pegar do pool
        poolConfig.setTestOnReturn(false); // Testar a conexão ao retornar ao pool


        return poolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig) {

        // RedisClusterConfiguration redisStandaloneConfiguration = new RedisClusterConfiguration();
        // redisStandaloneConfiguration.addClusterNode(new RedisNode(_cacheHostname, _port));
        // redisStandaloneConfiguration.setPassword(_cachekey);

        
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(_cacheHostname, _port);
        redisStandaloneConfiguration.setPassword(_cachekey);
        
        var jedisClientConfiguration = JedisClientConfiguration.builder()
                .readTimeout(Duration.ofMillis(_readTimeout))
                .connectTimeout(Duration.ofMillis(_connectTimeout))
                .usePooling()
                .poolConfig(poolConfig);

                jedisClientConfiguration.and().useSsl();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


    @Bean
    public DatetimeSingleton datetimeSingleton() {
        return new DatetimeSingleton();
    }

    @Bean
    public Jedis jedis() {
        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        Jedis jedis = new Jedis(_cacheHostname, _port, DefaultJedisClientConfig.builder()
        .password(_cachekey)
        .ssl(true)
        .build());

        return jedis;
    }

}
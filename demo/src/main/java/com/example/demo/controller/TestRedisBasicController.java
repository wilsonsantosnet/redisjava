package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;

@RestController
public class TestRedisBasicController {



    @GetMapping("/basic")
    public String getAccount() {
       

        boolean useSsl = true;
        String cacheHostname = "...";
        String cachekey = "...";




        // Connect to the Azure Cache for Redis over the TLS/SSL port using the key.
        Jedis jedis = new Jedis(cacheHostname, 6380, DefaultJedisClientConfig.builder()
        .password(cachekey)
        .ssl(useSsl)
        .build());



        var count=0;
        while (count <=1000) {

            try {



                // Simple PING command
                System.out.println( "\nCache Command  : Ping" );
                System.out.println( "Cache Response : " + jedis.ping());

                // Simple get and put of integral data types into the cache
                System.out.println( "\nCache Command  : GET Message" );
                System.out.println( "Cache Response : " + jedis.get("Message"));

                System.out.println( "\nCache Command  : SET Message" );
                System.out.println( "Cache Response : " + jedis.set("Message", "Hello! The cache is working from Java!"));

                // Demonstrate "SET Message" executed as expected...
                System.out.println( "\nCache Command  : GET Message" );
                System.out.println( "Cache Response : " + jedis.get("Message"));

                // Get the client list, useful to see if connection list is growing...
                System.out.println( "\nCache Command  : CLIENT LIST" );
                System.out.println( "Cache Response : " + jedis.clientList());
        

                Thread.sleep(1000);

            } catch (Exception e) {
                System.out.println(e.toString());

            }
            count++;
            
        }

        jedis.close();
        return "ok";

        
    }
}
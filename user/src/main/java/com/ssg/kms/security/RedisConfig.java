package com.ssg.kms.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.password}")
    private String password;
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
    	RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        //鍮꾨�踰덊샇 �꽕�젙�떆
        config.setPassword(password);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        // redisTemplate瑜� 諛쏆븘���꽌 set, get, delete瑜� �궗�슜
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        // setKeySerializer, setValueSerializer �꽕�젙
        // redis-cli�쓣 �넻�빐 吏곸젒 �뜲�씠�꽣瑜� 議고쉶 �떆 �븣�븘蹂� �닔 �뾾�뒗 �삎�깭濡� 異쒕젰�릺�뒗 寃껋쓣 諛⑹�
        
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
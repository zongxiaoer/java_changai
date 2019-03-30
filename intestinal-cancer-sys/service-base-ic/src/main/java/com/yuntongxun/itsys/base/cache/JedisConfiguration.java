package com.yuntongxun.itsys.base.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class JedisConfiguration {
	private final Logger log = LogManager.getLogger(JedisConfiguration.class);
	 @Bean(name="jedisPoolConfig")
     @ConfigurationProperties(prefix="spring.redis.pool")
     public JedisPoolConfig getRedisConfig(){
         JedisPoolConfig config = new JedisPoolConfig();
         return config;
     }
     
     @Bean(name="jedisConnectionFactory")
     @ConfigurationProperties(prefix="spring.redis")
     public JedisConnectionFactory getConnectionFactory(){
         JedisConnectionFactory factory = new JedisConnectionFactory();
         JedisPoolConfig config = getRedisConfig();
         factory.setPoolConfig(config);
         log.info("JedisConnectionFactory bean init success.");
         return factory;
     }
     
     
     @Bean
     public RedisTemplate<?, ?> getRedisTemplate(){
         RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
         return template;
     }
}

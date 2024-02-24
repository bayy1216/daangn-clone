package com.reditus.daangn.core.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}") private val host: String,
    @Value("\${spring.data.redis.port}") private val port: Int,
){
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()

        /**
         * 1. keySerializer: key에 대한 직렬화 방법을 설정합니다.
         *  - StringRedisSerializer: String 타입으로 직렬화합니다.
         * 2. valueSerializer: value에 대한 직렬화 방법을 설정합니다.
         *  - StringRedisSerializer: String 타입으로 직렬화합니다.
         */
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()

        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }

}
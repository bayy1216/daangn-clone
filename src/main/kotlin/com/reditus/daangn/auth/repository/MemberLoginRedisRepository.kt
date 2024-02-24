package com.reditus.daangn.auth.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class MemberLoginRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    /**
     * 로그인 실패 횟수를 저장합니다.
     * 1시간 후 만료됩니다.
     */
    fun setLoginFailCount(email: String, count: Int) {
        val key = generateLoginKey(email)
        val operationValues = redisTemplate.opsForValue()
        operationValues.set(key, count.toString())
        // 1시간 후 만료
        redisTemplate.expire(key, 60, TimeUnit.MINUTES)
    }


    /**
     * 로그인 실패 횟수를 가져옵니다.
     */
    fun getLoginFailCount(email: String): Int? {
        val key = generateLoginKey(email)
        val value =  redisTemplate.opsForValue().get(key)
        value?.let {
            return it.toString().toInt()
        }
        return null
    }

    /**
     * 로그인 실패 횟수를 삭제합니다.
     */
    fun deleteLoginFailCount(email: String) {
        val key = generateLoginKey(email)
        redisTemplate.delete(key)
    }

    fun getExpireTime(email: String): Long {
        val key = generateLoginKey(email)
        return redisTemplate.getExpire(key)
    }


    private fun generateLoginKey(email: String): String {
        return "login:$email"
    }
}
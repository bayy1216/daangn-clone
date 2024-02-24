package com.reditus.daangn.saleposts.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class SalePostSearchRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val LIMIT = 20L

    /**
     * 검색 키워드를 저장합니다.
     * SortedSet을 사용하여 저장하며, score는 현재 시간을 사용합니다.
     * [LIMIT] 만큼 최신 검색기록을 유지합니다.
     */
    fun saveSearchKeyword(memberId: Long, keyword: String) {
        val key = generateSearchKey(memberId)
        val now = System.currentTimeMillis()

        val opsZSet = redisTemplate.opsForZSet()
        opsZSet.add(key, keyword, now.toDouble())

        // 최신 검색기록 최대 LIMIT 개만 유지
        opsZSet.removeRange(key, -LIMIT, -LIMIT)
    }

    /**
     * 검색 키워드를 가져옵니다.
     * [LIMIT] 만큼 최신 검색기록을 가져옵니다.
     */
    fun getSearchKeywords(memberId: Long): Set<String> {
        val key = generateSearchKey(memberId)
        val opsZSet = redisTemplate.opsForZSet()
        val set= opsZSet.reverseRange(key, 0, LIMIT - 1)

        val resp = set?.map {
            it.toString()
        }?.toSet() ?: setOf()
        return resp
    }

    fun generateSearchKey(memberId: Long): String {
        return "search-keyword:$memberId"
    }
}
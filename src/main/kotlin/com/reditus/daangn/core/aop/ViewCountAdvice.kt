package com.reditus.daangn.core.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Aspect
@Component
class ViewCountAdvice(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    @AfterReturning("@annotation(viewCount)", returning="returnValue")
    fun countSaveAfter(joinPoint: JoinPoint, viewCount: ViewCount, returnValue: Any?) {
        // Redis Key 및 Value 생성
        var redisKey = viewCount.key
        var redisValue = viewCount.value

        // Redis Key 및 Value에 대한 정규식 패턴 생성
        val keyPattern = "#\\w+"
        val valuePattern = "#\\w+"
        val keyRegex = Regex(keyPattern)
        val valueRegex = Regex(valuePattern)

        // 매개변수 이름 및 값으로 Redis Key 및 Value 치환
        val method = (joinPoint.signature as MethodSignature).method
        val parameterValues = joinPoint.args
        method.parameters.forEachIndexed { index, parameter ->
            if (redisKey.contains("#${parameter.name}")) {
                redisKey = redisKey.replace(keyRegex, parameterValues[index].toString())
            }
            if (redisValue.contains("#${parameter.name}")) {
                redisValue = redisValue.replace(valueRegex, parameterValues[index].toString())
            }
        }

        // Redis Key 및 Value가 모두 채워졌는지 확인
        if (redisKey.isEmpty() || redisValue.isEmpty()) {
            throw RuntimeException("ViewCount AOP 구현 오류")
        }

        // Redis Set자료구조에 SADD 명령어를 통해 Key에 Value를 추가
        redisTemplate.opsForSet().add(redisKey, redisValue)
    }
}

/**
 * 조회수를 저장하기 위한 어노테이션
 * 함수 args를 통해 key와 value를 인식합니다.
 *
 * Redis Key 및 Value에 대한 정규식 패턴은 #\w+입니다.
 *
 * Redis에 저장될 Key 및 Value는 함수의 매개변수 이름으로 치환됩니다.
 *
 * ex) key = "salepost:view:#postId" -> 함수매개변수에 postId가 있어야 합니다.
 *
 * ex) value = "#memberId" -> 함수매개변수에 memberId가 있어야 합니다.
 *
 * ex) func(postId = 1, memberId = 1)-> redis에는 salepost:view:1 - 1 이 저장됩니다.
 *
 * Redis Set 자료구조에 SADD 명령어를 통해 Key에 Value를 추가
 * @param key Redis Key. #으로 시작하는 매개변수 이름을 사용하여 치환합니다.
 * @param value Redis Value. #으로 시작하는 매개변수 이름을 사용하여 치환합니다.
 * @sample com.reditus.daangn.saleposts.service.SalePostService.getSalePostDetail
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewCount(
    val key: String,
    val value: String,
)
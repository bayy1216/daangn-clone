package com.reditus.daangn.core.jwt

import com.reditus.daangn.core.domain.MemberType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expire-time}") private val expiration: Long,
    @Value("\${jwt.refresh-token-expire-time}") private val refreshExpiration: Long,
) {

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) }
    fun createToken(memberAuth: MemberAuth): JwtToken {
        val accessToken = generateToken(memberAuth=memberAuth, isAccessToken = true)
        val refreshToken = generateToken(memberAuth=memberAuth, isAccessToken = false)
        return JwtToken(accessToken, refreshToken)
    }
    private fun generateToken(memberAuth: MemberAuth, isAccessToken: Boolean): String{
        val now = Date()
        val expirationTime = if(isAccessToken) now.time + expiration else now.time + refreshExpiration
        return Jwts.builder()
            .subject(memberAuth.id.toString())
            .claim(TYPE, memberAuth.type)
            .claim(IS_ACCESS, isAccessToken)
            .issuedAt(now)
            .expiration(Date(expirationTime))
            .signWith(key, Jwts.SIG.HS256)
            .compact()
    }

    fun extractMemberAuth(rawToken: String): MemberAuth {
        val claims = extractClaims(rawToken)
        return MemberAuth(
            id = claims.subject.toLong(),
            type = MemberType.valueOf(claims[TYPE].toString())
        )
    }


    fun validateToken(rawToken: String, isAccessToken:Boolean = true) : Boolean {
        val claims = extractClaims(rawToken)
        val expiration = claims.expiration
        if(!isAccessToken && !(claims[IS_ACCESS] as Boolean)){
            return false
        }
        return expiration.after(Date())
    }
    private fun extractClaims(rawToken: String): Claims {
        try{
            return Jwts.parser().verifyWith(key).build().parse(rawToken).payload as Claims
        }catch (e: Exception){
            throw JwtException("토큰이 유효하지 않습니다.")
        }
    }

    companion object {
        const val TYPE = "type"
        const val IS_ACCESS = "isAccess"
    }
}
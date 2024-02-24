package com.reditus.daangn.core.utils

import org.springframework.stereotype.Component
import java.util.*

@Component
class DataUtils {
    fun extractAuthorizationHeader(header: String, isLogin:Boolean): String {
        val split = header.split("\\s".toRegex())
        val prefix = if(isLogin) "Basic" else "Bearer"
        if (split.size != 2 || split[0] != prefix) throw IllegalArgumentException("header의 Authorization 형식이 올바르지 않습니다.")
        return split[1]
    }

    fun decodeBase64(encoded: String): ByteArray {
        return Base64.getDecoder().decode(encoded)
    }
}


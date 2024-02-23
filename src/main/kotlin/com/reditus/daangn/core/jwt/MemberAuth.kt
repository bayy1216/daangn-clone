package com.reditus.daangn.core.jwt

import com.reditus.daangn.core.domain.MemberType

data class MemberAuth(
    val id: Long,
    val type: MemberType,
)

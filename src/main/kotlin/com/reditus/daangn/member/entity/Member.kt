package com.reditus.daangn.member.entity

import com.reditus.daangn.core.domain.MemberType
import com.reditus.daangn.core.entity.BaseTimeEntity
import com.reditus.daangn.member.domain.MemberState
import com.reditus.daangn.member.domain.Vendor
import jakarta.persistence.*

@Entity
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    var nickname: String?,
    var email: String,
    var password: String?,
    var profileImageUrl: String?,

    @Enumerated(EnumType.STRING)
    var vendor: Vendor,
    @Enumerated(EnumType.STRING)
    var state: MemberState,
    @Enumerated(EnumType.STRING)
    var type:MemberType,

    var fcmToken: String?,
    var alarm: Boolean,
) : BaseTimeEntity(){

    companion object{
        fun fixture(
            id: Long = 0,
            nickname: String? = null,
            email: String = "test@exmaple.com",
            password: String? = null,
            profileImageUrl: String? = null,
            vendor: Vendor = Vendor.EMAIL,
            state: MemberState = MemberState.ACTIVE,
            type: MemberType = MemberType.USER,
            fcmToken: String? = null,
            alarm: Boolean = true,
        ) = Member(
            id = id,
            nickname = nickname,
            email = email,
            password = password,
            profileImageUrl = profileImageUrl,
            vendor = vendor,
            state = state,
            type = type,
            fcmToken = fcmToken,
            alarm = alarm,
        )
    }
}
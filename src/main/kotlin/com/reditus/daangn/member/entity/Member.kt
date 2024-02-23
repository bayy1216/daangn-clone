package com.reditus.daangn.member.entity

import com.reditus.daangn.core.domain.MemberType
import com.reditus.daangn.core.entity.BaseTimeEntity
import com.reditus.daangn.member.domain.MemberState
import com.reditus.daangn.member.domain.Vendor
import jakarta.persistence.*

@Entity
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

    fun update(command: MemberUpdateCommand): Member {
        return Member(
            id = this.id,
            nickname = command.nickname ?: this.nickname,
            email = this.email,
            password = command.password ?: this.password,
            vendor = command.vendor ?: this.vendor,
            type = command.type ?: this.type,
            profileImageUrl = command.profileImageUrl ?: this.profileImageUrl,
            fcmToken = command.fcmToken ?: this.fcmToken,
            state = command.state ?: this.state,
            alarm = command.alarm ?: this.alarm,
        )
    }

    companion object{
        fun create(command: MemberCreateCommand): Member {
            return Member(
                nickname = command.nickname,
                email = command.email,
                password = command.password,
                vendor = command.vendor,
                type = command.type,
                profileImageUrl = command.profileImageUrl,
                fcmToken = command.fcmToken,
                state = MemberState.ACTIVE,
                alarm = true,
            )
        }
        fun fixture(
            id: Long? = null,
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

data class MemberCreateCommand(
    val nickname: String,
    val email: String,
    val password: String?,
    val vendor: Vendor,
    val type: MemberType,
    val profileImageUrl: String?,
    val fcmToken: String?,
)

data class MemberUpdateCommand(
    val nickname: String?,
    val password: String?,
    val profileImageUrl: String?,
    val vendor: Vendor?,
    val state: MemberState?,
    val type:MemberType?,
    val fcmToken: String?,
    val alarm: Boolean?,
)

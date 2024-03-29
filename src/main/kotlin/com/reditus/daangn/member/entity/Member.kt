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

    var nickname: String,
    @Column(unique = true)
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

    /**
     * 회원 탈퇴. 회원의 상태를 DELETED로 변경한다.
     */
    fun delete() {
        this.state = MemberState.DELETED
    }

    /**
     * 로그인시, 회원의 상태를 검증한다.
     */
    fun validCheckInLogin(){
        when(state){
            MemberState.ACTIVE -> {}
            MemberState.INACTIVE -> throw IllegalArgumentException("비활성화된 회원입니다.")
            MemberState.DELETED -> throw IllegalArgumentException("탈퇴한 회원입니다.")
            MemberState.BLOCKED -> throw IllegalArgumentException("제제로 인해 차단된 회원입니다.")
        }
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
            nickname: String = "심심한포로도",
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

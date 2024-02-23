package com.reditus.daangn.member.controller.dto.request

import com.reditus.daangn.core.domain.MemberType
import com.reditus.daangn.member.domain.Vendor
import com.reditus.daangn.member.entity.MemberCreateCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailSignupRequest(
    @field:NotBlank(message = "닉네임을 입력해주세요.")
    val nickname: String,

    @field:NotBlank(message = "이메일을 입력해주세요.")
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String,

    val profileImageUrl: String?,

    @field:NotBlank(message = "fcmToken을 입력해주세요.")
    val fcmToken: String,
){
    fun toCommand() = MemberCreateCommand(
        nickname = nickname,
        email = email,
        password = password,
        profileImageUrl = profileImageUrl,
        vendor = Vendor.EMAIL,
        type = MemberType.USER,
        fcmToken = fcmToken,
    )
}

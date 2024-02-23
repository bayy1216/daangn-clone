package com.reditus.daangn.member.entity

import jakarta.persistence.*
import java.time.LocalTime

@Entity
class MemberSetting(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
    @OneToOne
    @JoinColumn(name = "member_id")
    val member : Member,
    var memberPhoneNumber: String?,
    var noAlarmStartTime: LocalTime?,
    var noAlarmEndTime: LocalTime?,
) {

    companion object{
        fun fixture(
            id: Long = 0,
            member: Member = Member.fixture(),
            memberPhoneNumber: String? = null,
            noAlarmStartTime: LocalTime? = null,
            noAlarmEndTime: LocalTime? = null,
        ) = MemberSetting(
            id = id,
            member = member,
            memberPhoneNumber = memberPhoneNumber,
            noAlarmStartTime = noAlarmStartTime,
            noAlarmEndTime = noAlarmEndTime,
        )
    }
}
package com.reditus.daangn.member.entity

import jakarta.persistence.*
import java.time.LocalTime

@Entity
class MemberSetting(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,
    @OneToOne
    @JoinColumn(name = "member_id")
    val member : Member,
    var memberPhoneNumber: String?,
    var noAlarmStartTime: LocalTime?,
    var noAlarmEndTime: LocalTime?,
) {
    init {
        if(noAlarmStartTime != null || noAlarmEndTime != null){
            require(noAlarmStartTime != null && noAlarmEndTime != null)
            require(noAlarmStartTime!!.isBefore(noAlarmEndTime))
        }
    }

    companion object{
        fun fixture(
            id: Long? = null,
            member: Member = Member.fixture(),
            memberPhoneNumber: String? = null,
            noAlarmStartTime: LocalTime? = LocalTime.of(8,0),
            noAlarmEndTime: LocalTime? = LocalTime.of(22,0)
        ) = MemberSetting(
            id = id,
            member = member,
            memberPhoneNumber = memberPhoneNumber,
            noAlarmStartTime = noAlarmStartTime,
            noAlarmEndTime = noAlarmEndTime,
        )
    }
}
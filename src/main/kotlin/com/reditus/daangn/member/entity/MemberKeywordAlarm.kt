package com.reditus.daangn.member.entity

import jakarta.persistence.*

@Entity
class MemberKeywordAlarm(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    val keyword: String,
) {
    companion object {
        fun fixture(
            id: Long = 0,
            member: Member = Member.fixture(),
            keyword: String = "keyword",
        ) = MemberKeywordAlarm(
            id = id,
            member = member,
            keyword = keyword,
        )
    }
}
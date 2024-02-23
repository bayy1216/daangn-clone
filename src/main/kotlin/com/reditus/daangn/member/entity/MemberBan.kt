package com.reditus.daangn.member.entity

import jakarta.persistence.*

@Entity
class MemberBan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    @ManyToOne
    @JoinColumn(name = "bam_member_id")
    val bamMember: Member,
    var memberName: String,
    var banMemberAddress: String,
) {
    companion object {
        fun fixture(
            id: Long = 0,
            member: Member = Member.fixture(),
            bamMember: Member = Member.fixture(),
            memberName: String = "memberName",
            banMemberAddress: String = "banMemberAddress",
        ) = MemberBan(
            id = id,
            member = member,
            bamMember = bamMember,
            memberName = memberName,
            banMemberAddress = banMemberAddress,
        )
    }
}
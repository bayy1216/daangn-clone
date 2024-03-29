package com.reditus.daangn.member.entity

import com.reditus.daangn.core.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
class MemberBan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bam_member_id")
    val bamMember: Member,
    var memberName: String,
    var banMemberAddress: String,
) :BaseTimeEntity(){
    companion object {
        fun fixture(
            id: Long? = null,
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
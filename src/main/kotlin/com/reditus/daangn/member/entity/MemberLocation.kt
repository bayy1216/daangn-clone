package com.reditus.daangn.member.entity

import com.reditus.daangn.location.entity.Location
import jakarta.persistence.*

@Entity
class MemberLocation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    @ManyToOne
    @JoinColumn(name = "location_id")
    val location: Location,
    var current: Boolean,
) {
    companion object {
        fun fixture(
            id: Long? = null,
            member: Member = Member.fixture(),
            location: Location = Location.fixture(),
            current: Boolean = true,
        ) = MemberLocation(
            id = id,
            member = member,
            location = location,
            current = current,
        )
    }
}
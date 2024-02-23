package com.reditus.daangn.core.postconstructor

import com.reditus.daangn.location.entity.Location
import com.reditus.daangn.location.repository.LocationRepository
import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.member.repository.MemberRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class MakeData(
    private val memberRepository: MemberRepository,
    private val locationRepository: LocationRepository
) {
    @PostConstruct
    fun init(){
        val user = Member.fixture(
            email = "testuse13@naver.com",
            password = "1213",
        )
        memberRepository.save(user)
        val location = Location.fixture()
        val location2 = Location.fixture(
            baseAddress = "서울특별시",
            detailAddress = "강남",
            subAddress = "역상2동"
        )
        locationRepository.save(location)
        locationRepository.save(location2)
    }

}
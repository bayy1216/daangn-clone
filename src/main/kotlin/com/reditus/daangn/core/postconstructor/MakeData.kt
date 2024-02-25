package com.reditus.daangn.core.postconstructor

import com.reditus.daangn.location.entity.Location
import com.reditus.daangn.location.repository.LocationRepository
import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.member.repository.MemberRepository
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.entity.LocationInfo
import com.reditus.daangn.saleposts.entity.SalePost
import com.reditus.daangn.saleposts.entity.SalePostCreateCommand
import com.reditus.daangn.saleposts.repository.SalePostRepository
import jakarta.annotation.PostConstruct
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class MakeData(
    private val memberRepository: MemberRepository,
    private val salePostRepository: SalePostRepository,
    private val locationRepository: LocationRepository,
    private val env: Environment
) {
    @PostConstruct
    fun init() {
        if (env.activeProfiles.contains("prod")) {
            return
        }
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

        val post1 = SalePost.fixture(
            member = user,
            location = location,
        )
        salePostRepository.save(post1)
    }

}
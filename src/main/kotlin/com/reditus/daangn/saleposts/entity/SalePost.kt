package com.reditus.daangn.saleposts.entity

import com.reditus.daangn.member.entity.Member
import com.reditus.daangn.saleposts.domain.SalePostCategory
import com.reditus.daangn.saleposts.domain.SalePostStatus
import jakarta.persistence.*

@Entity
@Table(name = "sale_posts")
class SalePost(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    var title: String,
    var price: Int,
    var description: String,
    @Embedded
    var locationInfo: LocationInfo,
    @Enumerated(EnumType.STRING)
    var category: SalePostCategory,
    @Embedded
    var countInfo: CountInfo,
    @Enumerated(EnumType.STRING)
    var status:SalePostStatus,
) {

    companion object{
        fun fixture(
            id: Long? = null,
            member: Member = Member.fixture(),
            title: String = "title",
            price: Int = 1000,
            description: String = "description",
            locationInfo: LocationInfo = LocationInfo.fixture(),
            category: SalePostCategory = SalePostCategory.ELECTRONICS,
            countInfo: CountInfo = CountInfo.fixture(),
            status: SalePostStatus = SalePostStatus.ACTIVE,
        ) = SalePost(
            id = id,
            member = member,
            title = title,
            price = price,
            description = description,
            locationInfo = locationInfo,
            category = category,
            countInfo = countInfo,
            status = status
        )
    }
}

@Embeddable
class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val locationName: String
){
    init {
        require(latitude in -90.0..90.0)
        require(longitude in -180.0..180.0)
    }

    companion object {
        fun fixture(
            latitude: Double = 37.5665,
            longitude: Double = 126.9780,
            locationName: String = "Seoul"
        ) = LocationInfo(
            latitude = latitude,
            longitude = longitude,
            locationName = locationName
        )
    }
}

@Embeddable
class CountInfo(
    var viewCount: Int,
    var interestCount: Int,
    var chatCount: Int
){
    init {
        require(viewCount >= 0)
        require(interestCount >= 0)
        require(chatCount >= 0)
    }

    companion object {
        fun fixture(
            viewCount: Int = 0,
            interestCount: Int = 0,
            chatCount: Int = 0
        ) = CountInfo(
            viewCount = viewCount,
            interestCount = interestCount,
            chatCount = chatCount
        )
    }
}
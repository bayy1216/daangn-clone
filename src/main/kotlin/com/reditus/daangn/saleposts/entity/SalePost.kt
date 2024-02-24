package com.reditus.daangn.saleposts.entity

import com.reditus.daangn.core.entity.BaseTimeEntity
import com.reditus.daangn.location.entity.Location
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
    @ManyToOne
    @JoinColumn(name = "location_id")
    var location:Location,
    @Embedded
    var locationInfo: LocationInfo,
    @Enumerated(EnumType.STRING)
    var category: SalePostCategory,
    @Embedded
    var countInfo: CountInfo,
    @Enumerated(EnumType.STRING)
    var status:SalePostStatus,
) : BaseTimeEntity(){

    /**
     * 게시글 status를 DELETED로 변경
     */
    fun delete(){
        this.status = SalePostStatus.DELETED
    }

    /**
     * 게시글 정보를 업데이트
     */
    fun update(command: SalePostUpdateCommand) {
        this.title = command.title
        this.price = command.price
        this.description = command.description
        this.location = command.location
        this.locationInfo = command.locationInfo
        this.category = command.category
    }

    companion object{
        fun create(command: SalePostCreateCommand): SalePost {
            return SalePost(
                member = command.member,
                title = command.title,
                price = command.price,
                description = command.description,
                location = command.location,
                locationInfo = command.locationInfo,
                category = command.category,
                countInfo = CountInfo(viewCount = 0, interestCount = 0, chatCount = 0),
                status = SalePostStatus.ACTIVE
            )
        }
        fun fixture(
            id: Long? = null,
            member: Member = Member.fixture(),
            title: String = "title",
            price: Int = 1000,
            description: String = "description",
            location: Location = Location.fixture(),
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
            location = location,
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

data class SalePostCreateCommand(
    val member: Member,
    val title: String,
    val price: Int,
    val description: String,
    val location: Location,
    val locationInfo: LocationInfo,
    val category: SalePostCategory,
)

data class SalePostUpdateCommand(
    val title: String,
    val price: Int,
    val description: String,
    val location: Location,
    val locationInfo: LocationInfo,
    val category: SalePostCategory,
)